package com.publicissapient.backend.api.user.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Crypto {
    private String coin;
    private String wallet;
    private String network;
}
