package com.example.authservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public InvalidTokenException(String message, HttpStatus status) {
        super(message, status);
    }
}
