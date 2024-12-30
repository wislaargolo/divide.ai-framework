package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.request.UserTransactionCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.UserTransactionUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.CategoryResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserTransactionResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.UserTransaction;
import org.springframework.stereotype.Component;

@Component
public class UserTransactionMapper {

    public UserTransaction toEntity(UserTransactionCreateRequestDTO dto) {
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setAmount(dto.amount());
        userTransaction.setDescription(dto.description());
        userTransaction.setPaidAt(dto.paidAt());
        return userTransaction;
    }

    public UserTransaction toEntity(UserTransactionUpdateRequestDTO dto) {
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setAmount(dto.amount());
        userTransaction.setDescription(dto.description());
        userTransaction.setPaidAt(dto.paidAt());
        return userTransaction;
    }

    public UserTransactionResponseDTO toDto(UserTransaction userTransaction) {
        return new UserTransactionResponseDTO(
                userTransaction.getId(),
                userTransaction.getAmount(),
                userTransaction.getDescription(),
                new CategoryResponseDTO(
                        userTransaction.getCategory().getId(),
                        userTransaction.getCategory().getName(),
                        userTransaction.getCategory().getDescription(),
                        userTransaction.getCategory().getColor(),
                        userTransaction.getCategory().getExpense(),
                        userTransaction.getUser()
                ),
                userTransaction.getPaidAt(),
                userTransaction.getCreatedAt()
        );
    }
}
