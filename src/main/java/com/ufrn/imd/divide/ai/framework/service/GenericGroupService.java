package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.exception.ResourceNotFoundException;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericGroupService<T extends Group> {

    protected final GroupRepository<T> groupRepository;
    protected final UserService userService;
    protected final DebtService debtService;
    protected final UserValidationService userValidationService;

    protected GenericGroupService(GroupRepository<T> groupRepository,
                                  @Lazy UserService userService,
                                  DebtService debtService,
                                  UserValidationService userValidationService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.debtService = debtService;
        this.userValidationService = userValidationService;
    }

    public T findByIdIfExists(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grupo de ID " + groupId + " não encontrado."
                ));
    }

    public T findByCodeIfExists(String code) {
        return groupRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grupo com código " + code + " não encontrado."
                ));
    }

    public void validateAndUpdateGroupsForUserDeletion(User user) {
        List<T> groups = groupRepository.findByMembersId(user.getId());

        for (T group : groups) {
            debtService.validateUserDebts(group, user);

            if(group.getCreatedBy().equals(user)) {
                group.setDiscontinued(true);
            }

            group.getMembers().remove(user);
            groupRepository.save(group);

        }
    }

}
