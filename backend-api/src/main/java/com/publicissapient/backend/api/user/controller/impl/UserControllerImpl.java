package com.publicissapient.backend.api.user.controller.impl;

import com.publicissapient.backend.api.user.controller.UserController;
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

    public ResponseEntity<Void> loadUsers() {
        userService.loadUsers();
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    public ResponseEntity<List<User>> getUsersByRole(String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    public ResponseEntity<List<User>> getUsersSortedByAge(boolean ascending) {
        return ResponseEntity.ok(userService.getUsersSortedByAge(ascending));
    }

    public ResponseEntity<Optional<User>> searchUsers(Long id, String ssn) {
        return ResponseEntity.ok(userService.searchUsers(id, ssn));
    }
}
