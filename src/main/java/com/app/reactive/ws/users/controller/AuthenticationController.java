package com.app.reactive.ws.users.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.reactive.ws.users.data.dto.request.AuthenticationRequest;
import com.app.reactive.ws.users.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

        private final AuthenticationService authService;

        @PostMapping("/login")
        public Mono<ResponseEntity<Object>> login(
                        @RequestBody Mono<AuthenticationRequest> authenticationRequestMono) {

                // return Mono.just(ResponseEntity.ok().build());
                return authenticationRequestMono
                .flatMap(authRequest -> 
                        authService.authenticate(authRequest.getEmail(), authRequest.getPassword()))
                .map(authResult -> ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authResult.get("token"))
                        .header("UserId", authResult.get("userId"))
                        .build())
                .onErrorReturn(BadCredentialsException.class, 
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid Credentials"))
                .onErrorReturn(Exception.class, ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
                

        }

}
