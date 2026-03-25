package com.photo.security;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignupRequest {
    private String username;
    private String password;
}
