package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.closure.GroupClosureStrategy;
import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.JoinGroupRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;
import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.framework.exception.ResourceNotFoundException;
import com.ufrn.imd.divide.ai.framework.mapper.GroupMapper;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import com.ufrn.imd.divide.ai.framework.util.AttributeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class GroupService<T extends Group, R extends GroupRepository<T>,
                          CRequestDTO extends GroupCreateRequestDTO,
                          URequestDTO extends GroupUpdateRequestDTO,
                          ResponseDTO extends GroupResponseDTO> extends GenericGroupService<T, R> {

    protected final GroupMapper<T, CRequestDTO, URequestDTO, ResponseDTO> groupMapper;
    protected final GroupTransactionRepository groupTransactionRepository;
    protected final GroupClosureStrategy<T> groupClosureStrategy;

    protected GroupService(R repository,
                           GroupMapper<T, CRequestDTO, URequestDTO, ResponseDTO> groupMapper,
                           UserService userService,
                           DebtService debtService,
                           UserValidationService userValidationService,
                           GroupTransactionRepository groupTransactionRepository,
                           GroupClosureStrategy<T> groupClosureStrategy) {
        super(repository, userService, debtService, userValidationService);
        this.groupMapper = groupMapper;
        this.groupTransactionRepository = groupTransactionRepository;
        this.groupClosureStrategy = groupClosureStrategy;
    }


    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void checkDeleteGroup() {
        List<T> groups = repository.findAll();
        for (T group : groups) {
            if (groupClosureStrategy.shouldCloseGroup(group)){
                groupTransactionRepository.deleteAllByGroup(group);
                repository.delete(group);
            }
        }
    }

    @Transactional
    public void delete(Long groupId) {
        T group = findByIdIfExists(groupId);

        userValidationService.validateUser(group.getCreatedBy().getId(),
                "Apenas o dono do grupo pode removê-lo.");

        groupTransactionRepository.deleteAllByGroup(group);
        repository.delete(group);
    }


    @Transactional
    public ResponseDTO save(CRequestDTO dto) {
        validateUser(dto.getCreatedBy());

        T group = groupMapper.toEntity(dto);

        validateBeforeSave(group);

        group.setCode(generateUniqueCode());
        group.setMembers(new ArrayList<>());
        group.getMembers().add(group.getCreatedBy());

        return groupMapper.toDto(repository.save(group));
    }

    public ResponseDTO update(Long groupId, URequestDTO dto) {
        T group = findByIdIfExists(groupId);

        validateUser(group.getCreatedBy().getId());
        BeanUtils.copyProperties(dto, group, AttributeUtils.getNullOrBlankPropertyNames(dto));

        validateBeforeSave(group);

        return groupMapper.toDto(repository.save(group));
    }

    private void validateUser(Long createdBy) {
        userService.findById(createdBy);
        userValidationService.validateUser(createdBy);
    }

    public List<ResponseDTO> findAllByUserId(Long userId) {
        userService.findById(userId);
        List<T> groups = repository.findByMembersId(userId);

        return groups.
                stream().map(groupMapper::toDto)
                .collect(Collectors.toList());

    }

    public ResponseDTO findById(Long groupId) {
        T group = findByIdIfExists(groupId);
        return groupMapper.toDto(group);
    }

    public ResponseDTO joinGroupByCode(JoinGroupRequestDTO dto) {
        T group = findByCodeIfExists(dto.code());
        User user = userService.findById(dto.userId());

        validateBeforeJoin(group, user);

        group.getMembers().add(user);

        return groupMapper.toDto(repository.save(group));
    }

    public ResponseDTO deleteMember(Long groupId, Long userId) {
        T group = findByIdIfExists(groupId);
        User user = userService.findById(userId);
        validateBeforeDeleteMember(group, user);

        group.getMembers().remove(user);
        return groupMapper.toDto(repository.save(group));
    }

    public ResponseDTO leaveGroup(Long groupId, Long userId) {
        T group = findByIdIfExists(groupId);
        User user = userService.findById(userId);
        validateBeforeLeave(group, user);

        group.getMembers().remove(user);
        return groupMapper.toDto(repository.save(group));
    }

    protected abstract void validateBeforeSave(T group);

    protected void validateBeforeJoin(T group, User user) {
        if(group.isDiscontinued()) {
            throw new BusinessException(
                    "O grupo não aceita mais membros, foi descontinuado.",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (group.getMembers().contains(user)) {
            throw new BusinessException(
                    "Este usuário já é membro do grupo.",
                    HttpStatus.BAD_REQUEST
            );
        }

    }

    protected void validateBeforeDeleteMember(T group, User user) {
        userValidationService.validateUser(
                group.getCreatedBy().getId(),
                "Apenas o dono do grupo pode remover um membro.");

        validateUserRemoval(group, user);
    }

    protected void validateBeforeLeave(T group, User user) {
        userValidationService.validateUser(user.getId());
        validateUserRemoval(group, user);
    }

    protected void validateUserRemoval(T group, User user) {
        validateUserMemberOfGroup(group, user);
        validateGroupOwner(group, user);
        debtService.validateUserDebts(group, user);
    }

    protected void validateGroupOwner(T group, User user) {
        if (group.getCreatedBy().equals(user)) {
            throw new BusinessException(
                    "O proprietário do grupo não pode sair ou ser removido. " +
                            "Para encerrar o grupo, é necessário excluí-lo.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    protected void validateUserMemberOfGroup(T group, User user) {
        if (!group.getMembers().contains(user)) {
            throw new BusinessException(
                    "O usuário não faz parte deste grupo.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    protected String generateUniqueCode() {
        String code;
        do {
            String uuid = UUID.randomUUID().toString();
            code = uuid.substring(0, 6);
        } while (repository.existsByCode(code));
        return code;
    }

}
