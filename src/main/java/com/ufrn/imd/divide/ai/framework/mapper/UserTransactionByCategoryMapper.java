package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.response.UserTransactionByCategoryDTO;
import com.ufrn.imd.divide.ai.framework.model.VWUserTransactionByCategory;
import org.springframework.stereotype.Component;

@Component
public class UserTransactionByCategoryMapper {

    public UserTransactionByCategoryDTO toDto(VWUserTransactionByCategory vwUserTransactionByCategory) {
        return new UserTransactionByCategoryDTO(
                vwUserTransactionByCategory.getCategoryId(),
                vwUserTransactionByCategory.getCategoryName(),
                vwUserTransactionByCategory.getUserId(),
                vwUserTransactionByCategory.getAmount(),
                vwUserTransactionByCategory.getMonth(),
                vwUserTransactionByCategory.getYear(),
                vwUserTransactionByCategory.getColor()
        );
    }

}