package com.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Ad alanı boş olamaz")
    @Size(min = 3, message = "İsim en az 3 karakter olmalı")
    private String username;

    @Email(message = "Geçerli bir email adresi giriniz")
    @NotBlank(message = "Email alanı boş olamaz")
    private String email;

    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    @NotBlank(message = "Şifre alanı boş olamaz")
    private String password;

}
