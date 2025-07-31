package com.authh.springJwt.Utils;





import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Swagger Specification ",
                version = "1.0",
                description = "Swagger documentation for Emhicha Backend Application",
                contact = @Contact(name = "Emhicha Backend Application")
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {

                @Server(url = "http://localhost:8080"),
                @Server(url = "https://9489e6066c96.ngrok-free.app")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .packagesToScan("com.authh.springJwt.Authentication.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("all-apis")
                .packagesToScan("com.authh.springJwt")
                .build();
    }
}






