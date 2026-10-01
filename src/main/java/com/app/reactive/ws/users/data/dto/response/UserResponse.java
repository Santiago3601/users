package com.app.reactive.ws.users.data.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class UserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
