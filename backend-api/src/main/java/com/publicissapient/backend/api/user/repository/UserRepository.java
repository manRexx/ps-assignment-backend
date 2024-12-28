package com.publicissapient.backend.api.user.repository;

import com.publicissapient.backend.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(String role);

    List<User> findAllByOrderByAgeAsc();

    List<User> findAllByOrderByAgeDesc();

    @Query(
            "SELECT u FROM User u " +
                    "WHERE " +
                        "u.id = :id " +
                    "OR " +
                        "u.ssn = :ssn"
    )
    Optional<User> searchUser(
            @Param("id") Long id,
            @Param("ssn") String ssn
    );
}
