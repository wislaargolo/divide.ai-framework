package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.request.DebtRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.DebtUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.DebtResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.Debt;
import com.ufrn.imd.divide.ai.framework.model.GroupTransaction;
import com.ufrn.imd.divide.ai.framework.model.User;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DebtMapper {

    public Debt toEntity(DebtRequestDTO dto) {
        Debt debt = new Debt();
        debt.setUser(new User(dto.userId()));
        debt.setAmount(dto.amount());
        return debt;
    }

    public Debt toEntity(DebtUpdateRequestDTO dto) {
        Debt debt = new Debt();
        debt.setUser(new User(dto.id()));
        debt.setAmount(dto.amount());
        return debt;
    }

    public DebtResponseDTO toDTO(Debt debt) {
        if (debt == null) {
            return null;
        }
        return new DebtResponseDTO(
                debt.getId(),
                debt.getAmount(),
                new UserResponseDTO(debt.getUser().getId(), debt.getUser().getEmail(),
                        debt.getUser().getFirstName(), debt.getUser().getLastName(),
                        debt.getUser().getPhoneNumber()),
                Date.from(debt.getCreatedAt().toInstant( ZoneOffset.UTC )),
                Date.from(debt.getPaidAt().toInstant( ZoneOffset.UTC ))
        );
    }

    public Debt toEntity(DebtRequestDTO dto, GroupTransaction savedGroupTransaction) {
        Debt debt = toEntity(dto);
        debt.setGroupTransaction(savedGroupTransaction);
        return debt;
    }

    public List<DebtResponseDTO> toDTOList(List<Debt> debts) {
        return debts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
