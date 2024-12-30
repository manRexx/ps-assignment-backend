package com.publicissapient.backend.api.user.service.impl;

import com.publicissapient.backend.api.user.dto.UserDto;
import com.publicissapient.backend.api.user.model.User;
import com.publicissapient.backend.api.user.repository.UserRepository;
import com.publicissapient.backend.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${external.api.users-url}")
    String usersDataUrl;

    public void loadUsers() {
        System.out.println("<---- Loading Users Data from API ---->");
        try {
            UserDto res = restTemplate.getForObject(usersDataUrl, UserDto.class);
            if (res != null && !res.getUsers().isEmpty()) {
                userRepository.saveAll(res.getUsers());
                System.out.println("<---- SUCCESS!!! : Loading Users Data from API ---->");
            }
        } catch (Exception e) {
            System.out.println("<---- ERROR!!! : Loading Users Data from API ---->");
            throw new RuntimeException ("Failed to load users from external API", e);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> getUsersSortedByAge(boolean ascending) {
        return ascending ? userRepository.findAllByOrderByAgeAsc() : userRepository.findAllByOrderByAgeDesc();
    }

    public Optional<User> searchUsers(Long id) {
        return userRepository.searchUserById(id);
    }
}
