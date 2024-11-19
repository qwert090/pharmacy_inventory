package com.example.userservice.controller;


import com.example.userservice.client.AuthServiceClient;
import com.example.userservice.dto.SessionDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserFilterDto;
import com.example.userservice.service.UserService;
import com.example.userservice.util.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthServiceClient authServiceClient;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(
            @RequestParam(required = false, name = "first-name") String firstName,
            @RequestParam(required = false, name = "last-name") String lastName,
            @RequestParam(required = false, name = "email") String email,
            @PageableDefault(sort = "firstName") Pageable pageable
    ) {
        log.info("Request for users /api/v1/users with parameters: firstname={}, lastname={}, email={}.",
                firstName, lastName, email);
        UserFilterDto userFilter = new UserFilterDto(firstName, lastName, email);
        return ResponseEntity.ok(userService.getUsers(userFilter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") UUID userId) {
        log.info("Request for user /api/v1/users/{}", userId);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        String token = ApplicationUtils.extractJwtFromBearerToken(jwt);
        SessionDto sessionDto = authServiceClient.getSession(token).getBody();
        return ResponseEntity.ok(userService.getUserById(sessionDto.getUserId()));
    }

    @PostMapping("/by-ids")
    public ResponseEntity<List<UserDto>> getUsersByIds(@RequestBody List<UUID> ids) {
        log.info("Request for users /api/v1/users/ids with ids: {}", ids);
        return ResponseEntity.ok(userService.getAllUsersByIds(ids));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        log.info("Request for create user /api/v1/users with param: {}", userDto);
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
