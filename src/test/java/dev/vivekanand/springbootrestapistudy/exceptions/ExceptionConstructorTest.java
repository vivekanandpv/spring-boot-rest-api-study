package dev.vivekanand.springbootrestapistudy.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionConstructorTest {

    @Test
    void domainExceptionSupportsEveryConstructor() {
        // Verify that every DomainException constructor preserves its supplied state.
        Throwable cause = new IllegalStateException("cause");
        DomainException empty = new DomainException();
        assertThat(empty).hasNoCause();
        assertThat(empty.getMessage()).isNull();
        assertThat(new DomainException("message")).hasMessage("message");
        assertThat(new DomainException(cause)).hasCause(cause);
        assertThat(new DomainException("message", cause)).hasMessage("message").hasCause(cause);
        DomainException configured = new DomainException("message", cause, false, false);
        assertThat(configured).hasMessage("message").hasCause(cause);
        assertThat(configured.getSuppressed()).isEmpty();
    }

    @Test
    void resourceNotFoundExceptionSupportsEveryConstructor() {
        // Verify that every ResourceNotFoundException constructor preserves its supplied state.
        Throwable cause = new IllegalStateException("cause");
        ResourceNotFoundException empty = new ResourceNotFoundException();
        assertThat(empty).hasNoCause();
        assertThat(empty.getMessage()).isNull();
        assertThat(new ResourceNotFoundException("message")).hasMessage("message");
        assertThat(new ResourceNotFoundException(cause)).hasCause(cause);
        assertThat(new ResourceNotFoundException("message", cause)).hasMessage("message").hasCause(cause);
        ResourceNotFoundException configured = new ResourceNotFoundException("message", cause, false, false);
        assertThat(configured).hasMessage("message").hasCause(cause);
        assertThat(configured.getSuppressed()).isEmpty();
    }
}
