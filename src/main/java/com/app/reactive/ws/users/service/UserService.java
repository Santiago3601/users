package com.app.reactive.ws.users.service;

import java.util.UUID;

import com.app.reactive.ws.users.data.dto.request.CreateUserRequest;
import com.app.reactive.ws.users.data.dto.response.UserResponse;

import reactor.core.publisher.Mono;

/**
 * UserService
 */
public interface UserService {

    Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequest);
    Mono<UserResponse> getUserById(UUID userId);

}
