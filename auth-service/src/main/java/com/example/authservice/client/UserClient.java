package com.example.authservice.client;


import com.example.authservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(
        name = "${discovery-service.application-name.user-service}",
        path = "/api/v1/users"
)
public interface UserClient {

    @PostMapping
    ResponseEntity<UserDto> createUser(UserDto userDto);


    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUser(@PathVariable(name = "id") UUID userId);
}
