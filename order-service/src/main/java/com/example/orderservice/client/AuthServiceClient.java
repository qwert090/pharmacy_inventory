package com.example.orderservice.client;


import com.example.orderservice.dto.SessionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${discovery-service.application-name.auth-service}",
        path = "/api/v1/account"
)
public interface AuthServiceClient {

    @PostMapping("/session")
    ResponseEntity<SessionDto> getSession(@RequestBody String jwt);
}
