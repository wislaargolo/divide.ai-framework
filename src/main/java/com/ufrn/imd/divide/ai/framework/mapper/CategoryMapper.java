package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.request.CategoryRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.CategoryResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        Category category = new Category();
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        category.setColor(categoryDTO.color());
        category.setExpense(categoryDTO.expense());
        return category;
    }

    public CategoryResponseDTO toDto(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getColor(),
                category.getExpense(),
                category.getUser()
        );
    }

}