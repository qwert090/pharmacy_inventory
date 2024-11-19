package com.example.authservice.controller;


import com.example.authservice.dto.SessionDto;
import com.example.authservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @GetMapping("/profile")
    public void profile() {
        log.info("profile");
    }

    @PostMapping("/session")
    public ResponseEntity<SessionDto> getSession(@RequestBody String jwt) {
        log.info("Post request 'api/v1/account/session' by jwt '{}'", jwt);
        SessionDto session = userAccountService.getSession(jwt);
        return ResponseEntity.ok(session);
    }
}
