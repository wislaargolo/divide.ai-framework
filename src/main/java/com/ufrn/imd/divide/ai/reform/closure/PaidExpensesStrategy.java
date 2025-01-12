package com.ufrn.imd.divide.ai.reform.closure;

import com.ufrn.imd.divide.ai.framework.closure.GroupClosureStrategy;
import com.ufrn.imd.divide.ai.framework.model.GroupTransaction;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import com.ufrn.imd.divide.ai.reform.model.Reform;
import com.ufrn.imd.divide.ai.reform.repository.CustomGroupTransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PaidExpensesStrategy implements GroupClosureStrategy<Reform> {

    private final CustomGroupTransactionRepository customGroupTransactionRepository;
    private final GroupTransactionRepository groupTransactionRepository;

    public PaidExpensesStrategy(CustomGroupTransactionRepository customGroupTransactionRepository, GroupTransactionRepository groupTransactionRepository) {
        this.customGroupTransactionRepository = customGroupTransactionRepository;
        this.groupTransactionRepository = groupTransactionRepository;
    }

    @Override
    public boolean shouldCloseGroup(Reform group) {
        return !groupTransactionRepository.findByGroupId(group.getId()).isEmpty() && !customGroupTransactionRepository.existsByGroupAndDebtsPaidAtIsNull(group);
    }
}
