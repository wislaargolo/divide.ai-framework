package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.dto.request.UserTransactionCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.UserTransactionUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserTransactionByCategoryDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserTransactionByMonthResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserTransactionResponseDTO;
import com.ufrn.imd.divide.ai.framework.exception.ResourceNotFoundException;
import com.ufrn.imd.divide.ai.framework.mapper.UserTransactionByCategoryMapper;
import com.ufrn.imd.divide.ai.framework.mapper.UserTransactionByMonthMapper;
import com.ufrn.imd.divide.ai.framework.mapper.UserTransactionMapper;
import com.ufrn.imd.divide.ai.framework.model.Category;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.model.UserTransaction;
import com.ufrn.imd.divide.ai.framework.repository.UserTransactionRepository;
import com.ufrn.imd.divide.ai.framework.repository.VWUserTransactionByCategoryRepository;
import com.ufrn.imd.divide.ai.framework.repository.VWUserTransactionsGroupedByMonthRepository;
import com.ufrn.imd.divide.ai.framework.util.AttributeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTransactionService {

    private final UserTransactionRepository userTransactionRepository;
    private final VWUserTransactionsGroupedByMonthRepository vwUserTransactionsGroupedByMonthRepository;
    private final VWUserTransactionByCategoryRepository vwUserTransactionByCategoryRepository;
    private final UserTransactionByMonthMapper userTransactionByMonthMapper;
    private final UserTransactionByCategoryMapper userTransactionByCategoryMapper;
    private final UserTransactionMapper userTransactionMapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UserValidationService userValidationService;

    public UserTransactionService(UserTransactionRepository userTransactionRepository,
                                  VWUserTransactionsGroupedByMonthRepository vwUserTransactionsGroupedByMonthRepository, VWUserTransactionByCategoryRepository vwUserTransactionByCategoryRepository, UserTransactionByMonthMapper userTransactionByMonthMapper, UserTransactionByCategoryMapper userTransactionByCategoryMapper, UserTransactionMapper userTransactionMapper,
                                  UserService userService,
                                  CategoryService categoryService,
                                  UserValidationService userValidationService) {
        this.userTransactionRepository = userTransactionRepository;
        this.vwUserTransactionsGroupedByMonthRepository = vwUserTransactionsGroupedByMonthRepository;
        this.vwUserTransactionByCategoryRepository = vwUserTransactionByCategoryRepository;
        this.userTransactionByMonthMapper = userTransactionByMonthMapper;
        this.userTransactionByCategoryMapper = userTransactionByCategoryMapper;
        this.userTransactionMapper = userTransactionMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.userValidationService = userValidationService;
    }

    public UserTransactionResponseDTO save(UserTransactionCreateRequestDTO dto) {
        userValidationService.validateUser(dto.userId());
        User user = userService.findById(dto.userId());
        Category category = categoryService.getCategoryByIdIfExists(dto.categoryId());

        UserTransaction userTransaction = userTransactionMapper.toEntity(dto);
        userTransaction.setUser(user);
        userTransaction.setCategory(category);
        if (category.getExpense()) {
            userTransaction.setAmount(-dto.amount());
        }

        return userTransactionMapper.toDto(userTransactionRepository.save(userTransaction));
    }

    public List<UserTransactionResponseDTO> findAllByUserId(Long userId) {
        userValidationService.validateUser(userId);

        User user = userService.findById(userId);
        List<UserTransaction> userTransactions = userTransactionRepository.findAllByUser(user);

        return userTransactions.stream()
                .map(userTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserTransactionResponseDTO update(Long transactionId,
                                             UserTransactionUpdateRequestDTO dto) {
        UserTransaction userTransaction = findByIdIfExists(transactionId);
        Category category = categoryService.getCategoryByIdIfExists(dto.categoryId());
        userTransaction.setCategory(category);
        userValidationService.validateUser(userTransaction.getUser().getId());

        if (dto.paidAt() == null) {
            userTransaction.setPaidAt(null);
        }

        BeanUtils.copyProperties(dto, userTransaction, AttributeUtils.getNullOrBlankPropertyNames(dto));
        if (category.getExpense()) {
            userTransaction.setAmount(-dto.amount());
        }
        return userTransactionMapper.toDto(userTransactionRepository.save(userTransaction));
    }

    public void delete(Long transactionId) {
        UserTransaction userTransaction = findByIdIfExists(transactionId);
        userValidationService.validateUser(userTransaction.getUser().getId());
        userTransactionRepository.delete(userTransaction);
    }

    public UserTransactionResponseDTO findById(Long userId) {
        return userTransactionMapper.toDto(findByIdIfExists(userId));
    }

    public List<UserTransactionByMonthResponseDTO> getUserTransactionsGroupedByMonth(Long userId) {
        return vwUserTransactionsGroupedByMonthRepository.findByUserId(userId)
                .stream()
                .map(userTransactionByMonthMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserTransactionByCategoryDTO> getUserTransactionsGroupedByCategory(Long userId) {
        return vwUserTransactionByCategoryRepository.findByUserId(userId)
                .stream()
                .map(userTransactionByCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserTransaction findByIdIfExists(Long id) {
        return userTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transação de ID " + id + " não encontrada."
                ));
    }
}
