package com.publicissapient.backend.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadResult {
    private int dataLoaded;
    private String message;
}
