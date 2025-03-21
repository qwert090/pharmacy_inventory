package com.example.authservice.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventUpdateDto {
    @NotBlank(message = "Тип события не может быть пустым")
    @Pattern(regexp = "^[\\w\\s\\-().,\"]{1,50}$",
            message = "Недопустимые символы в наименовании типа события или длинна превышает 50")
    private String type;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 10, max = 200, message = "Описание должно быть от 10 до 200 символов")
    private String description;

    @NotNull(message = "Дата события не может быть пустой")
    @FutureOrPresent(message = "Дата события не может быть в прошлом")
    private LocalDate eventDate;
}
