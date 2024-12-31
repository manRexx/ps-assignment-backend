package com.publicissapient.backend.api.user.dto;

import com.publicissapient.backend.api.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private List<User> users;
    private int total;
}
