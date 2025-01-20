package com.ufrn.imd.divide.ai.sporting.service;

import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import com.ufrn.imd.divide.ai.framework.service.DebtService;
import com.ufrn.imd.divide.ai.framework.service.GroupService;
import com.ufrn.imd.divide.ai.framework.service.UserService;
import com.ufrn.imd.divide.ai.framework.service.UserValidationService;
import com.ufrn.imd.divide.ai.sporting.closure.InactivationStrategy;
import com.ufrn.imd.divide.ai.sporting.dto.request.SportingCreateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.dto.request.SportingUpdateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.dto.response.SportingResponseDTO;
import com.ufrn.imd.divide.ai.sporting.mapper.SportingMapper;
import com.ufrn.imd.divide.ai.sporting.model.Sporting;
import com.ufrn.imd.divide.ai.sporting.repository.SportingRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Profile("sporting")
@Service
public class SportingService  extends GroupService<Sporting, SportingRepository, SportingCreateRequestDTO, SportingUpdateRequestDTO, SportingResponseDTO> {
    protected SportingService(SportingRepository repository, SportingMapper sportingMapper,
                              @Lazy UserService userService,
                              DebtService debtService, UserValidationService userValidationService,
                              GroupTransactionRepository groupTransactionRepository,
                              InactivationStrategy inactivationStrategy) {
        super(repository, sportingMapper, userService, debtService, userValidationService, groupTransactionRepository, inactivationStrategy);
    }

    @Override
    protected void validateBeforeSave(Sporting sporting) {
        Optional<Sporting> duplicate = repository.findByLocalAndOccurrenceDateAndCreatedBy(
                sporting.getLocal(),
                sporting.getOccurrenceDate(),
                sporting.getCreatedBy()
        );
        if (duplicate.isPresent() && (sporting.getId() == null || !sporting.getId().equals(duplicate.get().getId()))) {
            throw new BusinessException(
                    "Já existe um evento esportivo com o mesmo local, data de ocorrência criado pelo usuário.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
