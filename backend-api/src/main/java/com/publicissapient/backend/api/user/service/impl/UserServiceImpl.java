package com.publicissapient.backend.api.user.service.impl;

import com.publicissapient.backend.api.common.LoadResult;
import com.publicissapient.backend.api.user.model.User;
import com.publicissapient.backend.api.user.repository.UserRepository;
import com.publicissapient.backend.api.user.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${external.api.users-url}")
    String usersDataUrl;

    public LoadResult loadUsers() {
        log.info("Loading users from external API");
        try {
            ExternalApiResponse response = restTemplate.getForObject(usersDataUrl, ExternalApiResponse.class);
            if (response == null) {
                return new LoadResult(0, "No response from external API");
            }

            List<User> savedUsers = userRepository.saveAll(response.getUsers());
            String successMessage = "Successfully loaded " + savedUsers.size() + " users";
            log.info(successMessage);

            return new LoadResult(savedUsers.size(), successMessage);

        } catch (Exception e) {
            log.error("Error loading users from external API: {}", e.getMessage());
            return new LoadResult(0, "Failed to load users: " + e.getMessage());
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

@Data
class ExternalApiResponse {
    private List<User> users;
    private int total;
}
