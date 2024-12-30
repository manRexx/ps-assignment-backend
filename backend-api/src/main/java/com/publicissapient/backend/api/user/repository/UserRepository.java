package com.publicissapient.backend.api.user.repository;

import com.publicissapient.backend.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(String role);

    List<User> findAllByOrderByAgeAsc();

    List<User> findAllByOrderByAgeDesc();

    Optional<User> searchUserById(Long id);
}
