package com.publicissapient.backend.api.user.controller;

import com.publicissapient.backend.api.user.model.User;
import com.publicissapient.backend.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/load")
    public ResponseEntity<Void> loadUsers() {
        userService.loadUsers();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @GetMapping("/sort/age")
    public ResponseEntity<List<User>> getUsersSortedByAge(@RequestParam(defaultValue = "true") boolean ascending) {
        return ResponseEntity.ok(userService.getUsersSortedByAge(ascending));
    }

    @GetMapping("/search")
    public ResponseEntity<Optional<User>> searchUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String ssn
    ) {
        return ResponseEntity.ok(userService.searchUsers(id, ssn));
    }
}
