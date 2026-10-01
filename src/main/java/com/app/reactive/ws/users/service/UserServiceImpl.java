package com.app.reactive.ws.users.service;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.reactive.ws.users.data.dto.request.CreateUserRequest;
import com.app.reactive.ws.users.data.dto.response.UserResponse;
import com.app.reactive.ws.users.data.entity.UserEntity;
import com.app.reactive.ws.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequestMono) {

        return createUserRequestMono
                .mapNotNull(request -> convertToEntity(request))
                .flatMap(entity -> userRepository.save(entity))
                .mapNotNull(entity -> convertToResponse(entity))
                .onErrorMap(DuplicateKeyException.class, 
                    exception -> new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage()));
    }

    @Override
    public Mono<UserResponse> getUserById(UUID userId) {
        return userRepository
            .findById(userId)
            .map(this::convertToResponse);
    }


    @Override
    public Flux<UserResponse> findAll(int page, int limit) {
        // if (page > 0) page = page -1; // This is used if I want the pages start from 1 instead of 0
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAllBy(pageable)
            .map(this::convertToResponse);
    }

    private UserEntity convertToEntity(CreateUserRequest createUserRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(createUserRequest, userEntity);
        return userEntity;
    }

    private UserResponse convertToResponse(UserEntity entity) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

}
