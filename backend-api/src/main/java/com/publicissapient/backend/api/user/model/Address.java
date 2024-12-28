package com.publicissapient.backend.api.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Embeddable
@Data
public class Address {
    private String address;
    private String city;
    private String state;
    private String stateCode;
    private String postalCode;

    @Embedded
    private Coordinates coordinates;

    private String country;
}