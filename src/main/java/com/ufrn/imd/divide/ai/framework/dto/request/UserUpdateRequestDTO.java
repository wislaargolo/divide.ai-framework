package com.ufrn.imd.divide.ai.framework.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequestDTO(
        String firstName,
        String lastName,
        @Email
        String email,
        String password,
        String phoneNumber
) {
}
