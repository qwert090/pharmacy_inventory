package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

public class IllegalEventAccessException extends BaseException {
    public IllegalEventAccessException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
