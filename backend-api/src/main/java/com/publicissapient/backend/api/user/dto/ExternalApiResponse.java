package com.publicissapient.backend.api.user.dto;

import com.publicissapient.backend.api.user.model.User;
import lombok.Data;

import java.util.List;

@Data
public class ExternalApiResponse {
    private List<User> users;
    private int total;
}
