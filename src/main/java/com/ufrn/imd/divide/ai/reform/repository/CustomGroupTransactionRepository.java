package com.ufrn.imd.divide.ai.reform.repository;

import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.repository.DebtRepository;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import org.springframework.context.annotation.Profile;

public interface CustomGroupTransactionRepository extends GroupTransactionRepository {
    boolean existsByGroupAndDebtsPaidAtIsNull(Group group);
}
