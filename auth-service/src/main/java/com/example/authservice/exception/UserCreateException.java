package com.example.authservice.exception;

import org.springframework.http.HttpStatus;

public class UserCreateException extends BaseException {
    public UserCreateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
