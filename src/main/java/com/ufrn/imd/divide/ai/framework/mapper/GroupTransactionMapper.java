package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupTransactionCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.GroupTransactionUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.GroupTransactionResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.Debt;
import com.ufrn.imd.divide.ai.framework.model.GroupTransaction;
import com.ufrn.imd.divide.ai.framework.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class GroupTransactionMapper {

    private final DebtMapper debtMapper;

    public GroupTransactionMapper(DebtMapper debtMapper) {
        this.debtMapper = debtMapper;
    }

    public GroupTransaction toEntity(GroupTransactionCreateRequestDTO dto) {
        GroupTransaction groupTransaction = new GroupTransaction();
        groupTransaction.setDebts(dto.debts().stream()
                .map(debtDto -> {
                    Debt debt = new Debt();
                    debt.setUser(new User(debtDto.userId()));
                    debt.setAmount(debtDto.amount());
                    return debt;
                })
                .collect(Collectors.toList()));
        mapToEntity(groupTransaction, dto.amount(), dto.description(), dto.dueDate());
        return groupTransaction;
    }

    public GroupTransaction toEntity(GroupTransactionUpdateRequestDTO dto) {
        GroupTransaction groupTransaction = new GroupTransaction();
        groupTransaction.setDebts(dto.debts().stream()
                .map(debtDto -> {
                    Debt debt = new Debt();
                    debt.setId(debtDto.id());
                    debt.setAmount(debtDto.amount());
                    return debt;
                })
                .collect(Collectors.toList()));
        mapToEntity(groupTransaction, dto.amount(), dto.description(), dto.dueDate());
        return groupTransaction;
    }

    private void mapToEntity(GroupTransaction groupTransaction, Double amount, String description, LocalDate dueDate) {
        groupTransaction.setAmount(amount);
        groupTransaction.setDescription(description);
        groupTransaction.setDueDate(dueDate);
    }

    public GroupTransactionResponseDTO toDTO(GroupTransaction groupTransaction) {
        if (groupTransaction == null) {
            return null;
        }

        return new GroupTransactionResponseDTO(
                groupTransaction.getId(),
                groupTransaction.getAmount(),
                groupTransaction.getDescription(),
                new GroupResponseDTO(groupTransaction.getGroup().getId(), groupTransaction.getGroup().getName()),
                new UserResponseDTO(groupTransaction.getCreatedBy().getId(), groupTransaction.getCreatedBy().getEmail(),
                        groupTransaction.getCreatedBy().getFirstName(), groupTransaction.getCreatedBy().getLastName(),
                        groupTransaction.getCreatedBy().getPhoneNumber()),
                debtMapper.toDTOList(groupTransaction.getDebts()),
                groupTransaction.getDueDate(),
                groupTransaction.getCreatedAt()
        );
    }
}
