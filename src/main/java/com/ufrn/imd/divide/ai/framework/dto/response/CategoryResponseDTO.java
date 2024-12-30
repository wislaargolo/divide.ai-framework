package com.ufrn.imd.divide.ai.framework.dto.response;

import com.ufrn.imd.divide.ai.framework.model.User;

public record CategoryResponseDTO(
        Long id,
        String name,
        String description,
        String color,
        Boolean expense,
        User userId
) {
}

