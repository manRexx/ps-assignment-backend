package com.publicissapient.backend.api.user.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
class Coordinates {
    private Double lat;
    private Double lng;
}
