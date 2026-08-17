package dev.vivekanand.springbootrestapistudy;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;
import static org.assertj.core.api.Assertions.assertThat;

class SpringBootRestApiStudyApplicationTest {

    @Test
    void mainStartsSpringApplication() {
        // Verify that the application entry point delegates startup to Spring Boot.
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            SpringBootRestApiStudyApplication.main(new String[]{"--test=true"});

            springApplication.verify(() -> SpringApplication.run(
                    SpringBootRestApiStudyApplication.class, "--test=true"));
        }
    }

    @Test
    void applicationCanBeConstructed() {
        // Verify that the application bootstrap class has a usable default constructor.
        assertThat(new SpringBootRestApiStudyApplication()).isNotNull();
    }
}
