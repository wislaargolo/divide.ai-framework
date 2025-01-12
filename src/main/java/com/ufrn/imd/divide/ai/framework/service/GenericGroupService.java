package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.exception.ResourceNotFoundException;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

public abstract class GenericGroupService<T extends Group, R extends GroupRepository<T>> {

    protected final R repository;
    protected final UserService userService;
    protected final DebtService debtService;
    protected final UserValidationService userValidationService;

    protected GenericGroupService(R repository,
                                  @Lazy UserService userService,
                                  DebtService debtService,
                                  UserValidationService userValidationService) {
        this.repository = repository;
        this.userService = userService;
        this.debtService = debtService;
        this.userValidationService = userValidationService;
    }

    public T findByIdIfExists(Long groupId) {
        return repository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grupo de ID " + groupId + " não encontrado."
                ));
    }

    public T findByCodeIfExists(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grupo com código " + code + " não encontrado."
                ));
    }

    public void validateAndUpdateGroupsForUserDeletion(User user) {
        List<T> groups = repository.findByMembersId(user.getId());

        for (T group : groups) {
            debtService.validateUserDebts(group, user);

            if(group.getCreatedBy().equals(user)) {
                group.setDiscontinued(true);
            }

            group.getMembers().remove(user);
            repository.save(group);

        }
    }

}
