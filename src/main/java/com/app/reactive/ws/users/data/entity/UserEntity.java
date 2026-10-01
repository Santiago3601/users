package com.app.reactive.ws.users.data.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Table(name = "USERS")
public class UserEntity {

    @Id 
    private UUID id;

    @Column(value = "FIRST_NAME")
    private String firstName;

    @Column(value = "LAST_NAME")
    private String lastName;

    private String email;

    private String password;
}
