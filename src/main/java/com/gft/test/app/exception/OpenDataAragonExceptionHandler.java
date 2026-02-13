package com.gft.test.app.exception;

import com.gft.test.app.model.OpenDataAragonError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class OpenDataAragonExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<OpenDataAragonError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return new ResponseEntity<>(OpenDataAragonError.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorTimestamp(LocalDateTime.now())
                .errorMsg(ex.getMessage() + ": " + request.getRequestURI())
                .build(),
                HttpStatus.NOT_FOUND);
    }
}
