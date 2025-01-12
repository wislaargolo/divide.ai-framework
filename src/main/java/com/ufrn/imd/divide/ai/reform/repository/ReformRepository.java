package com.ufrn.imd.divide.ai.reform.repository;

import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.reform.model.Reform;
import com.ufrn.imd.divide.ai.reform.model.ReformPriority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ReformRepository extends GroupRepository<Reform> {

    Optional<Reform> findByPriorityAndCreatedBy(ReformPriority priority, User createdBy);
    Optional<Reform> findByNameAndLocalAndOccurrenceDateAndCreatedBy(String name, String local,
                                                                     LocalDateTime occurrenceDate, User createdBy);
}
