package com.ufrn.imd.divide.ai.sporting.closure;

import com.ufrn.imd.divide.ai.framework.closure.GroupClosureStrategy;
import com.ufrn.imd.divide.ai.framework.model.GroupTransaction;
import com.ufrn.imd.divide.ai.sporting.repository.CustomSportingTransactionRepository;
import com.ufrn.imd.divide.ai.sporting.model.Sporting;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@Component
public class InactivationStrategy implements GroupClosureStrategy<Sporting> {
    private final CustomSportingTransactionRepository customGroupTransactionRepository;

    public InactivationStrategy(CustomSportingTransactionRepository customGroupTransactionRepository) {
        this.customGroupTransactionRepository = customGroupTransactionRepository;
    }

    @Override
    public boolean shouldCloseGroup(Sporting group) {
        Optional<GroupTransaction> lastTransactionOpt = customGroupTransactionRepository.findTopByGroupOrderByCreatedAtDesc(group);

        if (lastTransactionOpt.isPresent()) {
            GroupTransaction lastTransaction = lastTransactionOpt.get();
            LocalDateTime currentDate = LocalDateTime.now();
            long monthsBetween = ChronoUnit.MONTHS.between(lastTransaction.getCreatedAt(), currentDate);

            return monthsBetween > 3;
        }
        return false;
    }

}
