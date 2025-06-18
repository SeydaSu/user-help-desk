package com.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @Email(message = "Geçerli bir email adresi giriniz")
    @NotBlank(message = "Email alanı boş olamaz")
    private String email;

    @NotBlank(message = "Şifre alanı boş olamaz")
    private String password;

   

}
