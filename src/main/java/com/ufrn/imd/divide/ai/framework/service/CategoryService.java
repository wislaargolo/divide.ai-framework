package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.dto.request.CategoryRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.CategoryResponseDTO;
import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.framework.exception.ResourceNotFoundException;
import com.ufrn.imd.divide.ai.framework.mapper.CategoryMapper;
import com.ufrn.imd.divide.ai.framework.model.Category;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.CategoryRepository;
import com.ufrn.imd.divide.ai.framework.repository.UserRepository;
import com.ufrn.imd.divide.ai.framework.util.AttributeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryMapper categoryMapper, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public CategoryResponseDTO saveCategory(CategoryRequestDTO category) {
        List<Category> existingCategory = categoryRepository.findByNameIgnoreCaseAndUserIdAndExpense(
                category.name(),
                category.userId(),
                category.expense()
        );

        if (!existingCategory.isEmpty()) {
            throw new BusinessException(
                    "Categoria com o nome '" + category.name() + "' e tipo de despesa '" + (category.expense() ? "saída" : "entrada") + "' já existe para este usuário.",
                    HttpStatus.BAD_REQUEST
            );
        }
        User user = userRepository.findById(category.userId())
                .orElseThrow(() -> new Error("Usuário com ID '" + category.userId() + "' não encontrado."));
        if (category.expense() == null) {
            throw new BusinessException("O campo 'isExpense' não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
        Category c = categoryMapper.toEntity(category);
        c.setUser(user);
        return categoryMapper.toDto(categoryRepository.save(c));
    }

    public List<CategoryResponseDTO> getCategoriesBySubstring(String name) {

        List<Category> categories = categoryRepository
                .findByNameContainingIgnoreCase(name);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Não foram encontradas categorias com o nome: " + name);
        }

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> getCategoriesByUserId(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> getCategoriesByUserType(Long userId, boolean type) {
        List<Category> categories = categoryRepository.findByUserIdAndExpense(userId, type);

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = getCategoryByIdIfExists(id);
        return categoryMapper.toDto(category);
    }

    public Category getCategoryByIdIfExists(Long id) {
        Optional<Category> category =  categoryRepository.findById(id);
        if (category.isPresent()){
            return category.get();

        }
        throw new ResourceNotFoundException("Categoria não encontrada.");
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryDetails) {
        Optional<Category> c =  categoryRepository.findById(id);
        if (c.isPresent()){
            Category category = c.get();
            BeanUtils.copyProperties(categoryDetails, category, AttributeUtils.getNullOrBlankPropertyNames(categoryDetails));
            return categoryMapper.toDto(categoryRepository.save(category));
        }
        throw new ResourceNotFoundException("Categoria não encontrada.");
    }

    public List<CategoryResponseDTO> getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada."));

        categoryRepository.delete(category);
    }
}