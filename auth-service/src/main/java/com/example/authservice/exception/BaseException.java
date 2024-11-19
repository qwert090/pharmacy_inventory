package com.example.authservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BaseException(String message) {
        super(message);
        httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
