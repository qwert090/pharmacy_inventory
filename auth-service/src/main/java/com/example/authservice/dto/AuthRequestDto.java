package com.example.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    @NotBlank(message = "Email не может быть пустым")
    @Pattern(regexp = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$",
            message = "Введите действительный email")
    private String login;
    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Пароль должен содержать не менее 8 символов, включая цифры, строчные и заглавные буквы, а также специальные символы (@#$%^&+=!)")
    private String password;

    @Override
    public String toString() {
        return "AuthRequestDto{" +
                "login='" + login + '\'' +
                '}';
    }
}
