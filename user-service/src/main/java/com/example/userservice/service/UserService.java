package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserFilterDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserSpecificationService userSpecificationService;
    private final UserMapper userMapper;

    public Page<UserDto> getUsers(UserFilterDto filter, Pageable pageable) {
        log.info("Called getUsers with filter {}", filter);
        Specification<User> specification = userSpecificationService.getSpecification(filter);
        Page<User> users = userRepository.findAll(specification, pageable);
        log.info("Return users [{}] by specification on filter [{}]", users, filter);
        return users.map(userMapper::toDto);
    }

    public UserDto getUserById(UUID userId) {
        log.info("Called getUserById with eventId: {}", userId);
        return userRepository.findByIdAndDeleteDateIsNull(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<UserDto> getAllUsersByIds(List<UUID> userIds) {
        log.info("Called getAllUsersByIds with userIds: {}", userIds);
        return userRepository.findByIdInAndDeleteDateIsNull(userIds).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto createUser(UserDto userDto) {
        log.info("Called createUser with userDto: {}", userDto);
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        log.info("Created user {}", user);
        return userMapper.toDto(user);
    }
}
