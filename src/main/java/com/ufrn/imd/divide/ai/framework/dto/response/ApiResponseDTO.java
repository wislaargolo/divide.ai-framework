package com.ufrn.imd.divide.ai.framework.dto.response;

public record ApiResponseDTO<DTO>(
        boolean success,
        String message,
        DTO data,
        DTO error
) { }