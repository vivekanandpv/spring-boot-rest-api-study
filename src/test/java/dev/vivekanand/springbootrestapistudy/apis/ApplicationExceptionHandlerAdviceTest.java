package dev.vivekanand.springbootrestapistudy.apis;

import dev.vivekanand.springbootrestapistudy.exceptions.DomainException;
import dev.vivekanand.springbootrestapistudy.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationExceptionHandlerAdviceTest {
    private final ApplicationExceptionHandlerAdvice advice = new ApplicationExceptionHandlerAdvice();

    @Test
    void handlesResourceNotFoundAsNotFound() {
        // Verify that resource-not-found errors preserve their message and return HTTP 404.
        ResponseEntity<Map<String, String>> response = advice.handleException(
                new ResourceNotFoundException("missing"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("error", "missing");
    }

    @Test
    void handlesDomainErrorAsBadRequestWithSafeMessage() {
        // Verify that domain errors return HTTP 400 without exposing internal exception details.
        ResponseEntity<Map<String, String>> response = advice.handleException(new DomainException("internal"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("error", "Could not process your request. Check again.");
    }
}
