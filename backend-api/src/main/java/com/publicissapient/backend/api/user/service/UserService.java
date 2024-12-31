package com.publicissapient.backend.api.user.service;

import com.publicissapient.backend.api.user.model.User;
import com.publicissapient.backend.api.common.LoadResult;

import java.util.List;
import java.util.Optional;

public interface UserService {
    LoadResult loadUsers();
    List<User> getAllUsers();
    List<User> getUsersByRole(String role);
    List<User> getUsersSortedByAge(boolean ascending);
    Optional<User> searchUsers(Long id);
}
