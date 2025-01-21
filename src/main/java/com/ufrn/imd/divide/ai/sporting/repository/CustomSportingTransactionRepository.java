package com.ufrn.imd.divide.ai.sporting.repository;

import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.GroupTransaction;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

public interface CustomSportingTransactionRepository extends GroupTransactionRepository {
    Optional<GroupTransaction> findTopByGroupOrderByCreatedAtDesc(Group group);

}

