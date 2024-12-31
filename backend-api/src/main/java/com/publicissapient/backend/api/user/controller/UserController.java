package com.publicissapient.backend.api.user.controller;

import com.publicissapient.backend.api.user.dto.LoadUsersResponseDto;
import com.publicissapient.backend.api.user.dto.UserResponseDto;
import com.publicissapient.backend.api.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public interface UserController {
    @PostMapping("/load")
    ResponseEntity<LoadUsersResponseDto> loadUsers();

    @GetMapping
    ResponseEntity<UserResponseDto> getAllUsers();

    @GetMapping("/role")
    ResponseEntity<UserResponseDto> getUsersByRole(@RequestParam(required = true) String role);

    @GetMapping("/sort/age")
    ResponseEntity<UserResponseDto> getUsersSortedByAge(@RequestParam(defaultValue = "true") boolean ascending);

    @GetMapping("/search")
    ResponseEntity<Optional<User>> searchUsers(
            @RequestParam(required = false) Long id
    );
}
