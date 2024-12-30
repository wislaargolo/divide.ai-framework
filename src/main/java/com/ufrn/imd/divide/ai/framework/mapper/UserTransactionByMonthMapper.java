package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.response.UserTransactionByMonthResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.VWUserTransactionsGroupedByMonth;
import org.springframework.stereotype.Component;

@Component
public class UserTransactionByMonthMapper {

    public UserTransactionByMonthResponseDTO toDto(VWUserTransactionsGroupedByMonth vwUserTransactionsGroupedByMonth) {
        return new UserTransactionByMonthResponseDTO(
                vwUserTransactionsGroupedByMonth.getMonth(),
                vwUserTransactionsGroupedByMonth.getYear(),
                vwUserTransactionsGroupedByMonth.getTotalIncome(),
                vwUserTransactionsGroupedByMonth.getTotalExpenses()
        );
    }
}