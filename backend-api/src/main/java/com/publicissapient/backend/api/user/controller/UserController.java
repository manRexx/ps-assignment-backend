package com.publicissapient.backend.api.user.controller;

import com.publicissapient.backend.api.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public interface UserController {
    @PostMapping("/load")
    ResponseEntity<Void> loadUsers();

    @GetMapping
    ResponseEntity<List<User>> getAllUsers();

    @GetMapping("/role")
    ResponseEntity<List<User>> getUsersByRole(@RequestParam(required = true) String role);

    @GetMapping("/sort/age")
    ResponseEntity<List<User>> getUsersSortedByAge(@RequestParam(defaultValue = "true") boolean ascending);

    @GetMapping("/search")
    ResponseEntity<Optional<User>> searchUsers(
            @RequestParam(required = false) Long id
    );
}
