package com.ufrn.imd.divide.ai.framework.dto.response;

public record UserTransactionByMonthResponseDTO(
        int month,
        int year,
        double totalIncome,
        double totalExpenses
) {
}
