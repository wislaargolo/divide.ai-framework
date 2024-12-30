package com.ufrn.imd.divide.ai.framework.dto.response;

public record UserResponseDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
