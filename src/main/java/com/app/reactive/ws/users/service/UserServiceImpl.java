package com.app.reactive.ws.users.service;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.app.reactive.ws.users.data.dto.request.CreateUserRequest;
import com.app.reactive.ws.users.data.dto.response.UserResponse;
import com.app.reactive.ws.users.data.entity.UserEntity;
import com.app.reactive.ws.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
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
                .mapNotNull(entity -> convertToResponse(entity));
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
