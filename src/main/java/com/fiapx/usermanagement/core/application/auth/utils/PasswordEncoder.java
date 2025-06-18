package com.fiapx.usermanagement.core.application.auth.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    public static PasswordEncoder instance;

    private PasswordEncoder(){}

    public static PasswordEncoder getInstance(){
        if(instance == null){
            instance = new PasswordEncoder();
        }
        return instance;
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return hashPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return checkPassword(rawPassword.toString(), encodedPassword);
    }
}