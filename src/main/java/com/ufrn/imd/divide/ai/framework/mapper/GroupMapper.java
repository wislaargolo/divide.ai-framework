package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.sporting.dto.response.SportingResponseDTO;
import com.ufrn.imd.divide.ai.sporting.model.Sporting;

public abstract class GroupMapper<T extends Group, CRequestDTO extends GroupCreateRequestDTO,
                             URequestDTO extends GroupUpdateRequestDTO,
                             ResponseDTO extends GroupResponseDTO> {

    private final UserMapper userMapper;

    public GroupMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public abstract T toEntity(CRequestDTO groupCreateRequestDTO);

    public abstract T toEntity(URequestDTO groupUpdateRequestDTO);

    public abstract ResponseDTO toDto(T group);


    public T mapCreateFields(T group, CRequestDTO createDTO) {
        group.setName(createDTO.getName());
        group.setDescription(createDTO.getDescription());
        group.setCreatedBy(new User(createDTO.getCreatedBy()));
        group.setOccurrenceDate(createDTO.getOccurrenceDate());
        return group;
    }

    public T mapUpdateFields(T group, URequestDTO updateDTO) {
        group.setName(updateDTO.getName());
        group.setDescription(updateDTO.getDescription());
        group.setOccurrenceDate(updateDTO.getOccurrenceDate());
        return group;
    }

    public ResponseDTO mapCommonGroupFields(T group, ResponseDTO dto) {
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setCreatedBy(userMapper.toDto(group.getCreatedBy()));
        dto.setMembers(userMapper.toDtoList(group.getMembers()));
        dto.setCode(group.getCode());
        dto.setDiscontinued(group.isDiscontinued());
        dto.setOccurrenceDate(group.getOccurrenceDate());
        return dto;
    }


}
