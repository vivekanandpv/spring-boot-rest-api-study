package dev.vivekanand.massmutualspringday2;

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
public class MassmutualSpringDay2Application {

	public static void main(String[] args) {
		SpringApplication.run(MassmutualSpringDay2Application.class, args);
	}

}
