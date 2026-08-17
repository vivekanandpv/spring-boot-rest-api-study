package dev.vivekanand.springbootrestapistudy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Product REST API",
				version = "v1.0.0",
				description = "A learning project for Spring Boot"
		)
)
@SpringBootApplication
public class SpringBootRestApiStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiStudyApplication.class, args);
	}

}
