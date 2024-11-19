package com.example.authservice.repository;

import com.example.authservice.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findUserAccountByLoginAndDeleteDateIsNull(String login);
}
