package com.project.service.impl;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.config.JwtService;
import com.project.dto.AuthenticationRequest;
import com.project.dto.AuthenticationResponse;
import com.project.dto.RegisterRequest;
import com.project.kafka.KafkaProducerService;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final KafkaProducerService kafkaProducerService;

    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadCredentialsException("This email address is already in use.");
        }

        var user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()) 
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadCredentialsException("This email address is already in use.");
        }
        
        // Kafka event JSON oluştur
        String eventJson = """
            {
                "userId": "%s",
                "email": "%s",
                "eventType": "USER_REGISTERED",
                "timestamp": "%s"
             }
        """.formatted(user.getId(), user.getEmail(), LocalDateTime.now());

        // Kafka'ya event gönder
        kafkaProducerService.sendUserRegisteredEvent(eventJson);

        // JWT token oluştur - User objesi ile (kullanıcı bilgileri otomatik eklenecek)
        var jwtToken = jwtService.generateTokenForUser(user);
        
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId().longValue())
                .role(user.getRole())
                .build();
    }

    // User login - sadece USER rolündeki kullanıcılar
    public AuthenticationResponse loginUser(AuthenticationRequest request) {
        // Kullanıcıyı bul
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı."));

        // Role kontrolü - USER olmalı
        if (!user.getRole().equals(Role.USER)) {
            throw new BadCredentialsException("Bu panele sadece kullanıcılar giriş yapabilir.");
        }

        // Authentication işlemi
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Hatalı email veya şifre girdiniz.");
        }

        // Kafka event - User login
        String eventJson = """
            {
                "userId": "%s",
                "email": "%s",
                "role": "USER",
                "eventType": "USER_LOGGED_IN",
                "timestamp": "%s"
            }
        """.formatted(user.getId(), user.getEmail(), LocalDateTime.now());

        kafkaProducerService.sendUserLoginEvent(eventJson);

        // JWT token oluştur - User objesi ile (kullanıcı bilgileri otomatik eklenecek)
        var jwtToken = jwtService.generateTokenForUser(user);
        
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId().longValue())
                .role(user.getRole())
                .build();
    }

    // Admin login - sadece ADMIN rolündeki kullanıcılar
    public AuthenticationResponse loginAdmin(AuthenticationRequest request) {
        // Kullanıcıyı bul
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Admin kullanıcısı bulunamadı."));

        // Role kontrolü - ADMIN olmalı
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new BadCredentialsException("Bu panele sadece adminler giriş yapabilir.");
        }

        // Authentication işlemi
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Hatalı admin email veya şifre girdiniz.");
        }

        // Kafka event - Admin login
        String eventJson = """
            {
                "userId": "%s",
                "email": "%s",
                "role": "ADMIN",
                "eventType": "ADMIN_LOGGED_IN",
                "timestamp": "%s"
            }
        """.formatted(user.getId(), user.getEmail(), LocalDateTime.now());

        kafkaProducerService.sendUserLoginEvent(eventJson);

        // JWT token oluştur - User objesi ile (kullanıcı bilgileri otomatik eklenecek)
        var jwtToken = jwtService.generateTokenForUser(user);
        
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId().longValue())
                .role(user.getRole())
                .build();
    }
}