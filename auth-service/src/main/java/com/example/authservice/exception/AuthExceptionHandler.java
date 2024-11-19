package com.example.authservice.exception;

import com.example.authservice.dto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponseDto> handleBaseException(BaseException ex) {
        log.error("Handle exception with message: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = ex.getHttpStatus();
        if (httpStatus == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String message = ex.getMessage();
        ExceptionResponseDto responseDto = new ExceptionResponseDto(httpStatus, message);
        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleException(Exception ex) {
        log.error("Handle exception with message: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        ExceptionResponseDto responseDto = new ExceptionResponseDto(httpStatus, message);
        return new ResponseEntity<>(responseDto, httpStatus);
    }
}