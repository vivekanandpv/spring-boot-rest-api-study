package dev.vivekanand.springbootrestapistudy.apis;


import dev.vivekanand.springbootrestapistudy.exceptions.DomainException;
import dev.vivekanand.springbootrestapistudy.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandlerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandlerAdvice.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleException(ResourceNotFoundException exception) {
        LOGGER.warn(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", exception.getMessage()) //  review this core for data over exposure
        );
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleException(DomainException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Could not process your request. Check again.")
        );
    }
}
