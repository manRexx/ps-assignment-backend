package com.publicissapient.backend.api.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @NotNull(message = "ID cannot be null")
    private Long id;

    private String firstName;
    private String lastName;
    private String maidenName;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must not be greater than 100")
    private Integer age;

    private String gender;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String phone;
    private String username;
    private String password;
    private String birthDate;
    private String image;
    private String bloodGroup;
    private Double height;
    private Double weight;
    private String eyeColor;

    @Embedded
    private Hair hair;

    private String ip;

    @Embedded
    private Address address;

    private String macAddress;
    private String university;

    @Embedded
    private Bank bank;

    @Embedded
    private Company company;

    private String ein;
    private String ssn;
    private String userAgent;

    @Embedded
    private Crypto crypto;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(admin|user|moderator)$", message = "Role must be either ADMIN, USER, or MANAGER")
    private String role;
}
