package com.project.service;

import com.project.dto.AuthenticationRequest;
import com.project.dto.AuthenticationResponse;
import com.project.dto.RegisterRequest;

public interface IAuthenticationService {

    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse loginUser(AuthenticationRequest request);
    
    public AuthenticationResponse loginAdmin(AuthenticationRequest request);

}