package com.ufrn.imd.divide.ai.framework.repository;

import com.ufrn.imd.divide.ai.framework.model.VWUserTransactionByCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VWUserTransactionByCategoryRepository extends JpaRepository<VWUserTransactionByCategory, VWUserTransactionByCategory.PK> {
    List<VWUserTransactionByCategory> findByUserId(Long userId);
}
