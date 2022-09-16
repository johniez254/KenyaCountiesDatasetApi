package com.johnson.KenyaCountiesDatasetApi;

import com.johnson.KenyaCountiesDatasetApi.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@OpenAPIDefinition(
		info = @Info(title = "", version = "1.0.0"),
		servers = {@Server(url = "http://localhost:8080")},
		tags = {@Tag(name = "Kenyan Counties Dataset API", description = "A simple API for listing all Kenyan Counties, Constituencies, and their Wards")}
)
@SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class KenyaCountiesDatasetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KenyaCountiesDatasetApiApplication.class, args);
	}

}
