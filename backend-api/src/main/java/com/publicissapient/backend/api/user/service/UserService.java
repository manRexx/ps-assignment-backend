package com.publicissapient.backend.api.user.service;

import com.publicissapient.backend.api.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void loadUsers();
    List<User> getAllUsers();
    List<User> getUsersByRole(String role);
    List<User> getUsersSortedByAge(boolean ascending);
    Optional<User> searchUsers(Long id);
}
