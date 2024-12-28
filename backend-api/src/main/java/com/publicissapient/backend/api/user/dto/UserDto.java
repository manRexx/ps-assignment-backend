package com.publicissapient.backend.api.user.dto;

import com.publicissapient.backend.api.user.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    List<User> users;
    int total;
}
