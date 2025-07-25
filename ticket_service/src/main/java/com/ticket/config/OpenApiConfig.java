package com.ticket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition; 
import io.swagger.v3.oas.annotations.info.Contact; 
import io.swagger.v3.oas.annotations.info.Info; 
import io.swagger.v3.oas.annotations.servers.Server; 

@OpenAPIDefinition(
     info = @Info( 
        contact = @Contact( 
            name = "seyda", 
            email = "seyda@mail.com",
             url = "http://localhost:8082/api/v1" 
             ),
        description = "OpenApi documentation for ticket-service", 
        title = "OpenApi specification" ), 
        servers = { @Server( 
            description = "Local ENV", 
            url = "http://localhost:8082" 
            ) } )
 
public class OpenApiConfig { }