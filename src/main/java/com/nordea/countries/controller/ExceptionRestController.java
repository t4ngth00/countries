package com.nordea.countries.controller;

import com.nordea.countries.dto.response.RestClientExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
public class ExceptionRestController {
    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<RestClientExceptionDTO> handleEmployeeNotFoundException(RestClientResponseException e) {
        int statusCode = e.getRawStatusCode();
        String statusText = HttpStatus.valueOf(statusCode).getReasonPhrase();

        return ResponseEntity.status(statusCode).body(new RestClientExceptionDTO(statusCode, statusText));
    }
}
