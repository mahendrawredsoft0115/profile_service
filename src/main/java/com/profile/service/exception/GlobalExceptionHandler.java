package com.profile.service.exception;

import com.profile.service.dto.ApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApplicationResponse<String>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApplicationResponse.<String>builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApplicationResponse<String>> handleCustom(CustomException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApplicationResponse.<String>builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationResponse<String>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApplicationResponse.<String>builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationResponse<String>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApplicationResponse.<String>builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error: " + ex.getMessage())
                        .build()
        );
    }
}
