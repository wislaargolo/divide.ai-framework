package com.ufrn.imd.divide.ai.framework.dto.request;

public record OpenAIRequestDTO(
        Long userId,
        Long groupId,
        String prompt) {
}
