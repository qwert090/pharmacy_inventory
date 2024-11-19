package com.example.authservice.mapper;

import com.example.authservice.dto.RegistrationDto;
import com.example.authservice.dto.SessionDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    public UserDto toUserDto(RegistrationDto registrationDto) {
        return UserDto.builder()
                .email(registrationDto.getEmail())
                .age(registrationDto.getAge())
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .middleName(registrationDto.getMiddleName())
                .build();
    }

    public UserAccount toEntity(RegistrationDto registrationDto) {
        return new UserAccount()
                .setLogin(registrationDto.getEmail())
                .setPassword(registrationDto.getPassword());

    }

    public SessionDto toSession(UserAccount userAccount) {
        return SessionDto.builder()
                .userId(userAccount.getUserId())
                .role(userAccount.getRole().name())
                .email(userAccount.getLogin())
                .build();
    };
}
