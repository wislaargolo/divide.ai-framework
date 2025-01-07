package com.ufrn.imd.divide.ai.framework.dto.response;

public record ConfigOpenAIResponseDTO(
        String model,
        Double temperature,
        String systemPromptPath
) {
}
