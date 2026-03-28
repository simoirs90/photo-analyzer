package com.photo.security;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    String username;
    String password;
}
