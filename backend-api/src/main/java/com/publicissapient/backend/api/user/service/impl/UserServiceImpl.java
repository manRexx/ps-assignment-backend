package com.publicissapient.backend.api.user.service.impl;

import com.publicissapient.backend.api.common.LoadResult;
import com.publicissapient.backend.api.user.dto.ExternalApiResponse;
import com.publicissapient.backend.api.user.model.User;
import com.publicissapient.backend.api.user.repository.UserRepository;
import com.publicissapient.backend.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = {"users", "usersByRole", "usersSortedByAge"}, allEntries = true)
    public LoadResult loadUsers() {
        log.info("Loading users from external API and clearing all caches");
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

    @Cacheable(value = "users", unless = "#result.isEmpty()")
    public List<User> getAllUsers() {
        log.info("Fetching all users from database");
        List<User> res = userRepository.findAll();
        log.info("Successfully fetched {} users", res.size());
        return res;
    }

    @Cacheable(value = "usersByRole", key = "#role", unless = "#result.isEmpty()")
    public List<User> getUsersByRole(String role) {
        log.info("Fetching users by role: {}", role);
        List<User> users = userRepository.findByRole(role);
        log.info("Successfully fetched {} users with role: {}", users.size(), role);
        return users;
    }

    @Cacheable(value = "usersSortedByAge", key = "#ascending", unless = "#result.isEmpty()")
    public List<User> getUsersSortedByAge(boolean ascending) {
        log.info("Fetching users sorted by age, ascending: {}", ascending);
        List<User> users = ascending ? userRepository.findAllByOrderByAgeAsc() : userRepository.findAllByOrderByAgeDesc();
        log.info("Successfully fetched {} users sorted by age in {} order", users.size(), ascending ? "ascending" : "descending");
        return users;
    }

    public Optional<User> searchUsers(Long id) {
        log.info("Searching for user with id: {}", id);
        Optional<User> user = userRepository.searchUserById(id);
        if (user.isPresent()) {
            log.info("Successfully found user with id: {}", id);
        } else {
            log.warn("No user found with id: {}", id);
        }
        return user;
    }
}