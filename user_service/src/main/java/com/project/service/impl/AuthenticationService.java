package com.project.service.impl;


import java.time.LocalDateTime;

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
        var user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        // Kafka event JSON oluştur
        String eventJson = """
            {
                "userId": "%s",
                "email": "%s",
                "eventType": "USER_REGISTERED",
                "timestamp": "%s"
             }
        """.formatted(user.getId(), user.getEmail(), LocalDateTime.now());

        // Kafka’ya event gönder
        kafkaProducerService.sendUserRegisteredEvent(eventJson);


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId().longValue())
                .build();
    
    
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
    } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Hatalı şifre girdiniz.");
    } catch (UsernameNotFoundException e) {
        throw new UsernameNotFoundException("Kullanıcı bulunamadı.");
    }

    var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı."));


     // Kafka event JSON oluştur
    String eventJson = """
        {
            "userId": "%s",
            "email": "%s",
            "eventType": "USER_LOGGED_IN",
            "timestamp": "%s"
        }
    """.formatted(user.getId(), user.getEmail(), LocalDateTime.now());

    // Kafka'ya event gönder
    kafkaProducerService.sendUserLoginEvent(eventJson);

    
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .userId(user.getId().longValue())
            .build();
    
    
    }


    
}
