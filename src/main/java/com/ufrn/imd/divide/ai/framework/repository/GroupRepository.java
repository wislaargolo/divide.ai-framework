package com.ufrn.imd.divide.ai.framework.repository;

import com.ufrn.imd.divide.ai.framework.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository<T extends Group> extends JpaRepository<T, Long> {
    boolean existsByCode(String code);
    List<T> findByMembersId(Long userId);
    Optional<T> findByCode(String code);
}
