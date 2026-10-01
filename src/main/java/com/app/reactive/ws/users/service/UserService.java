package com.app.reactive.ws.users.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import com.app.reactive.ws.users.data.dto.request.CreateUserRequest;
import com.app.reactive.ws.users.data.dto.response.UserResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * UserService
 */
public interface UserService extends ReactiveUserDetailsService{

    Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequest);
    Mono<UserResponse> getUserById(UUID userId);
    Flux<UserResponse> findAll(int page, int limit);

}
