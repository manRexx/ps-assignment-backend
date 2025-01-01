package com.publicissapient.backend.api.user.controller;

import com.publicissapient.backend.api.user.dto.LoadUsersResponseDto;
import com.publicissapient.backend.api.user.dto.UserResponseDto;
import com.publicissapient.backend.api.user.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Validated
public interface UserController {
    @PostMapping("/load")
    ResponseEntity<LoadUsersResponseDto> loadUsers();

    @GetMapping
    ResponseEntity<UserResponseDto> getAllUsers();

    @GetMapping("/role")
    ResponseEntity<UserResponseDto> getUsersByRole(
            @RequestParam(required = true)
            @Pattern(regexp = "^(admin|user|moderator)$", message = "Invalid role, role must be Admin, User or Moderator")
            String role);

    @GetMapping("/sort/age")
    ResponseEntity<UserResponseDto> getUsersSortedByAge(
            @RequestParam(defaultValue = "true")
            boolean ascending);

    @GetMapping("/search")
    ResponseEntity<Optional<User>> searchUsers(
            @RequestParam(required = false)
            @Min(value = 1)
            @Max(value = 30)
            Long id
    );
}
