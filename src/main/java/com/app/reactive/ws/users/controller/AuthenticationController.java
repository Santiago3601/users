package com.app.reactive.ws.users.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.reactive.ws.users.data.dto.request.AuthenticationRequest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {


        @PostMapping("/login")
        public Mono<ResponseEntity<Void>> login(
                        @RequestBody Mono<AuthenticationRequest> authenticationRequestMono) {

                return Mono.just(ResponseEntity.ok().build());

        }

}
