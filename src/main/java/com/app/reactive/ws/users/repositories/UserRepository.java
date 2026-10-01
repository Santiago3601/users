package com.app.reactive.ws.users.repositories;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.app.reactive.ws.users.data.entity.UserEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

    Flux<UserEntity> findAllBy(Pageable pageable);

    Mono<UserEntity> findByEmail(String email);

}
