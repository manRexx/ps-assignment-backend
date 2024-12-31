package com.publicissapient.backend.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadUsersResponseDto {
    private int usersLoaded;
    private String status;
    private String message;
}
