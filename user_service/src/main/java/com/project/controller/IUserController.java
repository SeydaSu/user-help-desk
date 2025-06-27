package com.project.controller;

import org.springframework.security.core.userdetails.UserDetails;

import com.project.model.User;

public interface IUserController {
    
    public User getProfile(UserDetails userDetails);
}
