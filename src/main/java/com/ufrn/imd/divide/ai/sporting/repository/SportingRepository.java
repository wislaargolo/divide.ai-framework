package com.ufrn.imd.divide.ai.sporting.repository;

import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.sporting.model.Sporting;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SportingRepository extends GroupRepository<Sporting> {
    Optional<Sporting> findByLocalAndOccurrenceDateAndCreatedBy(String local, LocalDateTime occurrenceDate, User createdBy);

}
