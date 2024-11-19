package com.example.authservice.controller;


import com.example.authservice.dto.AuthRequestDto;
import com.example.authservice.dto.AuthResponseDto;
import com.example.authservice.dto.RegistrationDto;
import com.example.authservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegistrationDto registrationDto) {
        log.info("Post request '/api/v1/auth/register' with registrationDto: {}'", registrationDto);
        userAccountService.registerUser(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        log.info("Post request '/api/v1/auth/login' with authRequestDto: {}", authRequestDto);
        AuthResponseDto authResponseDto = userAccountService.authenticate(authRequestDto);
        log.info("response: {}", authResponseDto);
        return ResponseEntity.ok(authResponseDto);
    }
}
