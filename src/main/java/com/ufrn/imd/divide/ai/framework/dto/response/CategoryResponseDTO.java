package com.ufrn.imd.divide.ai.framework.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ufrn.imd.divide.ai.framework.model.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryResponseDTO(
        Long id,
        String name,
        String description,
        String color,
        Boolean expense,
        User userId
) {
}

