package com.app.reactive.ws.users.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.app.reactive.ws.users.data.entity.UserEntity;
import com.app.reactive.ws.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final UserRepository userRepository;

    @Override
    public Mono<Map<String, String>> authenticate(String username, String password) {

        return reactiveAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .then(getUserDetails(username)) // On the lambda when I don't need to use the value inside, instead of using map I can use then
                .map(this::createAuthResponse);
    }

    private Mono<UserEntity> getUserDetails(String userName) {
        return userRepository.findByEmail(userName);
    }

    private Map<String, String> createAuthResponse(UserEntity userEntity) {
        Map<String, String> result = new HashMap<>();
        result.put("userId", userEntity.getId().toString());
        result.put("token", "JWT Token");

        return result;
    }
}
