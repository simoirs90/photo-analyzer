package com.photo.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
