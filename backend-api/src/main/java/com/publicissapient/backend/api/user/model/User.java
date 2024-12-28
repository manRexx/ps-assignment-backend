package com.publicissapient.backend.api.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    Long id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    Integer age;

    @NotBlank
    String ssn;

    @NotBlank
    String role;
}
