package com.publicissapient.backend.api.user.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String maidenName;
    private Integer age;
    private String gender;
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

    private String role;
}
