package com.app.reactive.ws.users.data.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Table(name = "users")
public class UserEntity {

    @Id 
    private UUID id;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "last_name")
    private String lastName;

    private String email;

    private String password;
}
