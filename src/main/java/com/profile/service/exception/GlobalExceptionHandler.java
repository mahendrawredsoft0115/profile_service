package com.profile.service.exception;

import com.profile.service.dto.ApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Profile Service.
 * Catches and processes exceptions thrown during controller execution.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when a requested resource is not found.
     *
     * @param ex the ResourceNotFoundException
     * @return a 404 NOT FOUND response with an error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApplicationResponse<String>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApplicationResponse.<String>builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    /**
     * Handles custom application exceptions.
     *
     * @param ex the CustomException
     * @return a 400 BAD REQUEST response with a custom error message
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApplicationResponse<String>> handleCustom(CustomException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApplicationResponse.<String>builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    /**
     * Handles validation errors for invalid method arguments (e.g., @Valid).
     *
     * @param ex the MethodArgumentNotValidException
     * @return a 400 BAD REQUEST response with the first validation error message
     */
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

    /**
     * Handles any uncaught exceptions not explicitly mapped.
     *
     * @param ex the general Exception
     * @return a 500 INTERNAL SERVER ERROR response with a generic error message
     */
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
