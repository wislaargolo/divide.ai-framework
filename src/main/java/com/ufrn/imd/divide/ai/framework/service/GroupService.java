package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.JoinGroupRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;
import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.framework.mapper.GroupMapper;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.framework.util.AttributeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class GroupService<T extends Group,
                          CRequestDTO extends GroupCreateRequestDTO,
                          URequestDTO extends GroupUpdateRequestDTO,
                          ResponseDTO extends GroupResponseDTO> extends GenericGroupService<T> {

    protected final GroupRepository<T> groupRepository;
    protected final GroupMapper<T, CRequestDTO, URequestDTO, ResponseDTO> groupMapper;
    protected final UserService userService;
    protected final UserValidationService userValidationService;

    protected GroupService(GroupRepository<T> groupRepository,
                           GroupMapper<T, CRequestDTO, URequestDTO, ResponseDTO> groupMapper,
                           UserService userService,
                           DebtService debtService,
                           UserValidationService userValidationService) {
        super(groupRepository, userService, debtService, userValidationService);
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.userService = userService;
        this.userValidationService = userValidationService;
    }


    public void delete(Long groupId) {
        T group = findByIdIfExists(groupId);
        userValidationService.validateUser(group.getCreatedBy().getId(),
                "Apenas o dono do grupo pode removê-lo.");
        groupRepository.delete(group);
    }


    @Transactional
    public ResponseDTO save(CRequestDTO dto) {
        validateUser(dto.getCreatedBy());

        T group = groupMapper.toEntity(dto);

        validateBeforeSave(group);

        group.setCode(generateUniqueCode());
        group.setMembers(new ArrayList<>());
        group.getMembers().add(group.getCreatedBy());


        return groupMapper.toDto(groupRepository.save(group));
    }

    public ResponseDTO update(Long groupId, URequestDTO dto) {
        T group = findByIdIfExists(groupId);

        validateUser(group.getCreatedBy().getId());
        BeanUtils.copyProperties(dto, group, AttributeUtils.getNullOrBlankPropertyNames(dto));

        validateBeforeSave(group);

        return groupMapper.toDto(groupRepository.save(group));
    }

    private void validateUser(Long createdBy) {
        userService.findById(createdBy);
        userValidationService.validateUser(createdBy);
    }

    public abstract void validateBeforeSave(T group);


    public List<ResponseDTO> findAllByUserId(Long userId) {
        userService.findById(userId);
        List<T> groups = groupRepository.findByMembersId(userId);

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

        return groupMapper.toDto(groupRepository.save(group));
    }

    public ResponseDTO deleteMember(Long groupId, Long userId) {
        T group = findByIdIfExists(groupId);
        User user = userService.findById(userId);
        validateBeforeDelete(group, user);

        group.getMembers().remove(user);
        return groupMapper.toDto(groupRepository.save(group));
    }

    public ResponseDTO leaveGroup(Long groupId, Long userId) {
        T group = findByIdIfExists(groupId);
        User user = userService.findById(userId);
        validateBeforeLeave(group, user);

        group.getMembers().remove(user);
        return groupMapper.toDto(groupRepository.save(group));
    }

    private void validateBeforeJoin(T group, User user) {
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

    private void validateBeforeDelete(T group, User user) {
        userValidationService.validateUser(
                group.getCreatedBy().getId(),
                "Apenas o dono do grupo pode remover um membro.");

        validateUserRemoval(group, user);
    }

    private void validateBeforeLeave(T group, User user) {
        userValidationService.validateUser(user.getId());
        validateUserRemoval(group, user);
    }

    private void validateUserRemoval(T group, User user) {
        validateGroupOwner(group, user);
        validateUserMemberOfGroup(group, user);
        debtService.validateUserDebts(group, user);
    }

    private void validateGroupOwner(T group, User user) {
        if (group.getCreatedBy().equals(user)) {
            throw new BusinessException(
                    "O proprietário do grupo não pode sair ou ser removido. " +
                            "Para encerrar o grupo, é necessário excluí-lo.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validateUserMemberOfGroup(T group, User user) {
        if (!group.getMembers().contains(user)) {
            throw new BusinessException(
                    "O usuário não faz parte deste grupo.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private String generateUniqueCode() {
        String code;
        do {
            String uuid = UUID.randomUUID().toString();
            code = uuid.substring(0, 6);
        } while (groupRepository.existsByCode(code));
        return code;
    }

}
