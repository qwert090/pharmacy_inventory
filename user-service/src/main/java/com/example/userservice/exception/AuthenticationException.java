package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends BaseException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
