package com.smartfinance.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserExists(
            UserAlreadyExistsException ex) {

        log.error(
                "Exception occurred: {}",
                ex.getMessage()
        );

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "message",
                ex.getMessage()
        );

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }
}