package com.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.AuthenticationRequest;
import com.project.dto.AuthenticationResponse;
import com.project.dto.RegisterRequest;
import com.project.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") 
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request) {

                return ResponseEntity.ok(
                    authenticationService.register(request)
                );
    }
    


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        
        
            @RequestBody AuthenticationRequest request) {
                
                System.out.println("Login denemesi: " + request.getEmail());

                return ResponseEntity.ok(
                    authenticationService.login(request)
                );
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
 }
    
    
    
    

