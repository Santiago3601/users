package com.app.reactive.ws.users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.reactive.ws.users.dto.CreateUserResponse;
import com.app.reactive.ws.users.entity.CreateUserRequest;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public Mono<ResponseEntity<CreateUserResponse>> createUser(
            @RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {

        // // Imperative programming
        // CreateUserResponse response = new CreateUserResponse();
        // return Mono.just(response);
    
        // Map is used to transform the contents of the Mono
        return createUserRequest
                .map(request -> new CreateUserResponse(UUID.randomUUID(), request.getFirstName(), request.getLastName(),
                        request.getEmail()))
                .map(userResponse -> ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(URI.create("/users/" + userResponse.getId()))
                    .body(userResponse));

    }

}
