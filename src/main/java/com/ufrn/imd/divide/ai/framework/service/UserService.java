package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.dto.request.UserCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.UserUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserResponseDTO;
import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.framework.exception.ResourceNotFoundException;
import com.ufrn.imd.divide.ai.framework.mapper.UserMapper;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.UserRepository;
import com.ufrn.imd.divide.ai.framework.util.AttributeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidationService userValidationService;
    private final GenericGroupService<Group> groupService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       UserValidationService userValidationService,
                       GenericGroupService<Group> groupService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userValidationService = userValidationService;
        this.groupService = groupService;
    }

    @Transactional
    public void delete(Long userId) {
        userValidationService.validateUser(userId);
        User user = findById(userId);

        groupService.validateAndUpdateGroupsForUserDeletion(user);

        user.setActive(false);
        userRepository.save(user);
    }

    public UserResponseDTO update(UserUpdateRequestDTO dto, Long userId) {
        User user = findById(userId);
        validateBeforeUpdate(user);

        BeanUtils.copyProperties(dto, user, AttributeUtils.getNullOrBlankPropertyNames(dto));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserResponseDTO save(UserCreateRequestDTO dto) {
        User entity = userMapper.toEntity(dto);
        validateBeforeSave(entity);

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userMapper.toDto(userRepository.save(entity));
    }

    public User findById(Long userId) {
        return userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário de ID " + userId + " não encontrado."
                ));
    }

    private void validateBeforeUpdate(User entity) {
        userValidationService.validateUser(entity.getId());
        validateBeforeSave(entity);
    }

    private void validateBeforeSave(User entity) {
        validateEmail(entity.getEmail(), entity.getId());
        validatePhoneNumber(entity.getPhoneNumber(), entity.getId());
    }

    private void validateEmail(String email, Long id) {
        Optional<User> user = userRepository.findByEmailIgnoreCaseAndActiveTrue(email);
        if (user.isPresent() && (id == null || !user.get().getId().equals(id))) {
            throw new BusinessException(
                    "E-mail inválido: " + email + ". Um usuário cadastrado já utiliza este e-mail.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validatePhoneNumber(String phone, Long id) {
        Optional<User> user = userRepository.findByPhoneNumberAndActiveTrue(phone);
        if (user.isPresent() && (id == null || !user.get().getId().equals(id))) {
            throw new BusinessException(
                    "Número de telefone inválido: " + phone + ". Um usuário cadastrado já utiliza este número de telefone.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
