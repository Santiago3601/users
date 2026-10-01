package com.app.reactive.ws.users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.reactive.ws.users.entity.CreateUserRequest;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public Mono<CreateUserResponse> createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {
        
        // Imperative programming
        CreateUserResponse response = new CreateUserResponse();
        return Mono.just(response);
    }
    
    
}
