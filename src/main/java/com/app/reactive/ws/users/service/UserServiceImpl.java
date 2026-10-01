package com.app.reactive.ws.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.reactive.ws.users.data.dto.request.CreateUserRequest;
import com.app.reactive.ws.users.data.dto.response.UserResponse;
import com.app.reactive.ws.users.data.entity.UserEntity;
import com.app.reactive.ws.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequestMono) {

        return createUserRequestMono
                .flatMap(request -> convertToEntity(request))
                .flatMap(entity -> userRepository.save(entity))
                .mapNotNull(entity -> convertToResponse(entity));
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

    private Mono<UserEntity> convertToEntity(CreateUserRequest createUserRequest) {
        return Mono.fromCallable(() -> { // Utilize a thread waiting for being used in order to not being blocking the password encryption
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(createUserRequest, userEntity);
            userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
            return userEntity;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private UserResponse convertToResponse(UserEntity entity) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
        .map(userEntity -> User
            .withUsername(userEntity.getEmail())
            .password(userEntity.getPassword())
            .authorities(new ArrayList<>())
            .build()
        );
    }

}
