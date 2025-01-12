package com.ufrn.imd.divide.ai.framework.dto.response;

import java.util.Date;


public record DebtResponseDTO(
        Long id,
        Double amount,
        UserResponseDTO user,
        Date createdAt,
        Date paidAt
) {
}
