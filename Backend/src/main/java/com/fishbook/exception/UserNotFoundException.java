package com.fishbook.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found");
    }
}
