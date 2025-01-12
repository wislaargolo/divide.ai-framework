package com.ufrn.imd.divide.ai.reform.service;

import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import com.ufrn.imd.divide.ai.framework.service.DebtService;
import com.ufrn.imd.divide.ai.framework.service.GroupService;
import com.ufrn.imd.divide.ai.framework.service.UserService;
import com.ufrn.imd.divide.ai.framework.service.UserValidationService;
import com.ufrn.imd.divide.ai.reform.closure.PaidExpensesStrategy;
import com.ufrn.imd.divide.ai.reform.dto.request.ReformCreateRequestDTO;
import com.ufrn.imd.divide.ai.reform.dto.request.ReformUpdateRequestDTO;
import com.ufrn.imd.divide.ai.reform.dto.response.ReformResponseDTO;
import com.ufrn.imd.divide.ai.reform.mapper.ReformMapper;
import com.ufrn.imd.divide.ai.reform.model.Reform;
import com.ufrn.imd.divide.ai.reform.model.ReformPriority;
import com.ufrn.imd.divide.ai.reform.repository.ReformRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Profile("reform")
@Service
public class ReformService extends GroupService<Reform, ReformRepository, ReformCreateRequestDTO, ReformUpdateRequestDTO, ReformResponseDTO> {

    protected ReformService(ReformRepository repository, ReformMapper reformMapper,
                            @Lazy UserService userService,
                            DebtService debtService, UserValidationService userValidationService,
                            GroupTransactionRepository groupTransactionRepository,
                            PaidExpensesStrategy paidExpensesStrategy, ReformRepository reformRepository) {
        super(repository, reformMapper, userService, debtService, userValidationService, groupTransactionRepository, paidExpensesStrategy);
    }

    @Override
    protected void validateBeforeSave(Reform group) {
        validateDate(group);
        validatePriority(group);
        validateDuplicateReform(group);
    }

    private void validateDuplicateReform(Reform reform) {
        Optional<Reform> duplicate = repository.findByNameAndLocalAndOccurrenceDateAndCreatedBy(
                reform.getName(),
                reform.getLocal(),
                reform.getOccurrenceDate(),
                reform.getCreatedBy()
        );
        if (duplicate.isPresent() && (reform.getId() == null || !reform.getId().equals(duplicate.get().getId()))) {
            throw new BusinessException(
                    "Já existe uma reforma com o mesmo nome, local, data de ocorrência criada pelo usuário.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validatePriority(Reform reform) {
        if(reform.getPriority() == ReformPriority.URGENT) {
            Optional<Reform> other = repository.findByPriorityAndCreatedBy(ReformPriority.URGENT, reform.getCreatedBy());
            if (other.isPresent() && (reform.getId() == null || !reform.getId().equals(other.get().getId()))) {
                throw new BusinessException(
                        "Já existe uma reforma com prioridade Urgente criada pelo usuário.",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
    }

    private void validateDate(Reform reform) {
        if(reform.getOccurrenceDate().toLocalDate().isBefore(LocalDate.now()))  {
            throw new BusinessException(
                    "A data de início de uma reforma não pode ser anterior ao momento atual.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
