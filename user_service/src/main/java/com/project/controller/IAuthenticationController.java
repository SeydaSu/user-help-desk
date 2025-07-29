package com.project.controller;

import org.springframework.http.ResponseEntity;

import com.project.dto.AuthenticationRequest;
import com.project.dto.AuthenticationResponse;
import com.project.dto.RegisterRequest;


public interface IAuthenticationController {

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    public ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest request);

     public ResponseEntity<AuthenticationResponse> loginAdmin(AuthenticationRequest request);
}
