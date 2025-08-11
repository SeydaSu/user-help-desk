package com.ticket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "OpenAPI Specification",
        description = "OpenAPI documentation for ticket-service",
        contact = @Contact(name = "Seyda", email = "seyda@mail.com", url = "http://localhost:8082/api/v1")
    ),
    servers = @Server(description = "Local ENV", url = "http://localhost:8082")
)

public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            // Global security requirement: tüm endpointler için geçerli
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)         // HTTP tipi
                        .scheme("bearer")                       // bearer şeması
                        .bearerFormat("JWT")                    // JWT formatı
                        .in(SecurityScheme.In.HEADER)           // Header içinde gönderilir
                        .name("Authorization")                  // Header adı
                ));
    }
}
