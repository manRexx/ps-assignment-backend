package com.publicissapient.backend.api.user.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Hair {
    private String color;
    private String type;
}
