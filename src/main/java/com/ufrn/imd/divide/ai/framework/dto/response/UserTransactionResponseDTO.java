package com.ufrn.imd.divide.ai.framework.dto.response;

import java.time.LocalDateTime;

public record UserTransactionResponseDTO(
        Long id,
        Double amount,
        String description,
        CategoryResponseDTO category,
        LocalDateTime paidAt,
        LocalDateTime createdAt
) {
}
