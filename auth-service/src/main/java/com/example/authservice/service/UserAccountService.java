package com.example.authservice.service;


import com.example.authservice.client.UserClient;
import com.example.authservice.dto.*;
import com.example.authservice.entity.UserAccount;
import com.example.authservice.exception.AuthenticationException;
import com.example.authservice.exception.InvalidTokenException;
import com.example.authservice.exception.UserCreateException;
import com.example.authservice.exception.UserNotFoundException;
import com.example.authservice.mapper.UserAccountMapper;
import com.example.authservice.repository.UserAccountRepository;
import com.example.authservice.util.ApplicationUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserClient userClient;

    public void registerUser(RegistrationDto registrationDto) {
        registrationDto.setPassword(encodePassword(registrationDto.getPassword()));
        UserDto userDto = userAccountMapper.toUserDto(registrationDto);
        UserDto createdUser = createUser(userDto);
        UserAccount entity = userAccountMapper.toEntity(registrationDto)
                .setRole(UserAccount.Role.USER)
                .setUserId(createdUser.getId());
        UserAccount saved = userAccountRepository.save(entity);
        log.info("Success registration: {}", saved);
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        UserAccount userAccount = userAccountRepository.findUserAccountByLoginAndDeleteDateIsNull(authRequestDto.getLogin())
                .orElseThrow(() -> new AuthenticationException("Incorrect username or password"));
        checkPassword(authRequestDto.getPassword(), userAccount.getPassword());
        SessionDto session = userAccountMapper.toSession(userAccount);
        String jwt = jwtService.generateToken(session);
        Date expirationDate = jwtService.getExpirationDate(jwt);
        LocalDateTime validTo = ApplicationUtils.dateToLocalDateTime(expirationDate);
        log.info("Success login with param: {}", authRequestDto.getLogin());
        return AuthResponseDto.builder()
                .jwt(jwt)
                .validTo(validTo)
                .build();
    }

    public SessionDto getSession(String jwt) {
        log.info("get session jwt: {}", jwt);
        try {
            boolean tokenValid = jwtService.isTokenValid(jwt);
            if (!tokenValid) {
                throw new InvalidTokenException("Invalid jwt exception", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid jwt exception", e, HttpStatus.FORBIDDEN);
        }
        String login = jwtService.extractClaim(jwt, Claims.SUBJECT, String.class);
        UserAccount account = userAccountRepository.findUserAccountByLoginAndDeleteDateIsNull(login)
                .orElseThrow(() -> new UserNotFoundException("User not found exception", HttpStatus.FORBIDDEN));
        log.info("Return session by login: {}", login);
        return userAccountMapper.toSession(account);
    }

    private void checkPassword(String rawPass, String encodedPass) {
        boolean isPasswordMatch = passwordEncoder.matches(rawPass, encodedPass);
        if (!isPasswordMatch) {
            throw new AuthenticationException("Incorrect username or password");
        }
        log.info("Password check passed");
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private UserDto createUser(UserDto userDto) {
        UserDto createdUser = userClient.createUser(userDto).getBody();
        if (createdUser == null) {
            throw new UserCreateException("Error to create user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("User created: {}", createdUser);
        return createdUser;
    }
}
