package com.publicissapient.backend.api.user.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Coordinates {
    private Double lat;
    private Double lng;
}
