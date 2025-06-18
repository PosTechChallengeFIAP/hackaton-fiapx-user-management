package com.fiapx.usermanagement.core.application.exceptions;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String username) {
        super(String.format("Username '%s' is already in use.", username));
    }
}
