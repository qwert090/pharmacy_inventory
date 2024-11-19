package com.example.authservice.exception;

import org.springframework.http.HttpStatus;

public class IllegalEventAccessException extends BaseException {
    public IllegalEventAccessException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
