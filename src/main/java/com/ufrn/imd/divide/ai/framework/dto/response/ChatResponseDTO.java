package com.ufrn.imd.divide.ai.framework.dto.response;

import java.time.LocalDateTime;

public record ChatResponseDTO(
        String prompt,
        String response,
        Long userId,
        Long groupId,
        LocalDateTime createdAt
) {
}
