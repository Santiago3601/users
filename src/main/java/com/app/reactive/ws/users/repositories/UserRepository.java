package com.app.reactive.ws.users.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.app.reactive.ws.users.data.entity.UserEntity;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

}
