package com.publicissapient.backend.api.user.controller.impl;

import com.publicissapient.backend.api.common.LoadResult;
import com.publicissapient.backend.api.user.controller.UserController;
import com.publicissapient.backend.api.user.dto.LoadUsersResponseDto;
import com.publicissapient.backend.api.user.dto.UserResponseDto;
import com.publicissapient.backend.api.user.model.User;
import com.publicissapient.backend.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public ResponseEntity<LoadUsersResponseDto> loadUsers() {
        LoadResult result = userService.loadUsers();

        LoadUsersResponseDto response = new LoadUsersResponseDto(
                result.getDataLoaded(),
                result.getDataLoaded() > 0 ? "SUCCESS" : "FAILED",
                result.getMessage()
        );

        return result.getDataLoaded() > 0
                ? ResponseEntity.ok(response)
                : ResponseEntity.internalServerError().body(response);
    }

    public ResponseEntity<UserResponseDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new UserResponseDto(users, users.size()));
    }

    public ResponseEntity<UserResponseDto> getUsersByRole(String role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(new UserResponseDto(users, users.size()));
    }

    public ResponseEntity<UserResponseDto> getUsersSortedByAge(boolean ascending) {
        List<User> users = userService.getUsersSortedByAge(ascending);
        return ResponseEntity.ok(new UserResponseDto(users, users.size()));
    }

    public ResponseEntity<Optional<User>> searchUsers(Long id) {
        return ResponseEntity.ok(userService.searchUsers(id));
    }
}
