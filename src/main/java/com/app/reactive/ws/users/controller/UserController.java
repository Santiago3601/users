package com.app.reactive.ws.users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.reactive.ws.users.data.dto.request.CreateUserRequest;
import com.app.reactive.ws.users.data.dto.response.UserResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(
            @RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {

        // // Imperative programming
        // UserResponse response = new UserResponse();
        // return Mono.just(response);

        // Map is used to transform the contents of the Mono
        return createUserRequest
                .map(request -> new UserResponse(UUID.randomUUID(), request.getFirstName(), request.getLastName(),
                        request.getEmail()))
                .map(userResponse -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .location(URI.create("/users/" + userResponse.getId()))
                        .body(userResponse));

    }

    @GetMapping("/{userId}")
    public Mono<UserResponse> getUser(@PathVariable("userId") UUID userId) {
        return Mono.just(new UserResponse(UUID.randomUUID(), "Santiago", "Ruiz", "a@a.cc"));
    }

    @GetMapping
    public Flux<UserResponse> getUsers(
        @RequestParam(value = "offset", defaultValue = "0") int offset,
        @RequestParam(value = "limit", defaultValue = "50") int limit ) {
        return Flux.just(
            new UserResponse(UUID.randomUUID(), "Santiago", "Ruiz", "a@a.cc"),
            new UserResponse(UUID.randomUUID(), "Santiago", "Ruiz", "a@a.cc"),
            new UserResponse(UUID.randomUUID(), "Santiago", "Ruiz", "a@a.cc")
        );
    }


}
