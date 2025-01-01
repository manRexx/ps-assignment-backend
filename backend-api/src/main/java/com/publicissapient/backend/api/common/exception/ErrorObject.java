package com.publicissapient.backend.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorObject {
    String error;
    private String timestamp;
}
