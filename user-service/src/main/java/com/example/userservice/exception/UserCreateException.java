package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

public class UserCreateException extends BaseException {
    public UserCreateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
