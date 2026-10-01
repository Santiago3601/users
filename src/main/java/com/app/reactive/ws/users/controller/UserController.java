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
    public void createUser(@RequestBody @Valid Mono<CreateUserRequest> entity) {
        
    }
    
    
}
