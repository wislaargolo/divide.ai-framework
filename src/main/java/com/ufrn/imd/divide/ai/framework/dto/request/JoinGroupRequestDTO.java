package com.ufrn.imd.divide.ai.framework.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JoinGroupRequestDTO(
        @NotBlank
        String code,
        @NotNull
        Long userId
) {
}
