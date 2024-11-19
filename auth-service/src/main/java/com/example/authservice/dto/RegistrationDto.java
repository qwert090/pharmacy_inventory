package com.example.authservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    @NotBlank(message = "Email не может быть пустым")
    @Pattern(regexp = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$",
            message = "Введите действительный email")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Пароль должен содержать не менее 8 символов, включая цифры, строчные и заглавные буквы, а также специальные символы (@#$%^&+=!)")
    private String password;

    @NotBlank(message = "Подтверждение пароля не может быть пустым")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Подтверждение пароля должно соответствовать требованиям безопасности")
    private String confirmationPassword;

    @NotBlank(message = "Имя не может быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+$",
            message = "Имя должно содержать только буквы")
    @Size(max = 50, message = "Имя не может превышать 50 символов")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]*$",
            message = "Отчество должно содержать только буквы (если указано)")
    @Size(max = 50, message = "Отчество не может превышать 50 символов")
    private String middleName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+$",
            message = "Фамилия должна содержать только буквы")
    @Size(max = 50, message = "Фамилия не может превышать 50 символов")
    private String lastName;

    @NotNull(message = "Возраст обязателен")
    @Min(value = 12, message = "Возраст должен быть не менее 12 лет")
    @Max(value = 100, message = "Возраст не должен превышать 100 лет")
    private Integer age;

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
