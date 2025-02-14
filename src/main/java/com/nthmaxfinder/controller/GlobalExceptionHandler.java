package com.nthmaxfinder.controller;

import com.nthmaxfinder.exception.FileProcessingException;
import com.nthmaxfinder.exception.InvalidInputException;
import com.nthmaxfinder.model.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidInputException.class, FileProcessingException.class})
    public ResponseEntity<ApiErrorResponse> handleException(RuntimeException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}