package com.project.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
                        AuthenticationException authException) throws IOException {
        
        String path = request.getServletPath();
        
        // Swagger endpoint'leri için 401 döndürme - bu endpoint'ler açık olmalı
        if (path.startsWith("/swagger-ui") || 
            path.startsWith("/v3/api-docs") || 
            path.equals("/swagger-ui.html") ||
            path.startsWith("/webjars/") ||
            path.startsWith("/swagger-resources/") ||
            path.startsWith("/configuration/") ||
            path.equals("/")) {
            
            // Bu endpoint'ler için herhangi bir error döndürme
            return;
        }
        
        // Diğer endpoint'ler için normal 401 response
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, String> error = new HashMap<>();
        error.put("error", "Yetkisiz erişim: " + authException.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}