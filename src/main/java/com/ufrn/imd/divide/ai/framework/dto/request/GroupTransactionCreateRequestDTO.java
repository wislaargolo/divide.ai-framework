package com.ufrn.imd.divide.ai.framework.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record GroupTransactionCreateRequestDTO(
        @NotNull(message = "amount é obrigatório.")
        Double amount,
        @NotNull(message = "groupId é obrigatório.")
        Long groupId,
        @NotBlank(message = "description é obrigatório.")
        String description,
        @NotNull(message = "createdByUserId é obrigatório.")
        Long createdBy,
        @NotEmpty(message = "debts é obrigatório.")
        List<DebtRequestDTO> debts,
        @NotNull(message = "data de vencimento é obrigatório.")
        LocalDate dueDate
) {
}
