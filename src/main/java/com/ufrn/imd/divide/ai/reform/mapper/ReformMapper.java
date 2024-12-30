package com.ufrn.imd.divide.ai.reform.mapper;

import com.ufrn.imd.divide.ai.framework.mapper.GroupMapper;
import com.ufrn.imd.divide.ai.framework.mapper.UserMapper;
import com.ufrn.imd.divide.ai.reform.dto.request.ReformCreateRequestDTO;
import com.ufrn.imd.divide.ai.reform.dto.request.ReformUpdateRequestDTO;
import com.ufrn.imd.divide.ai.reform.dto.response.ReformResponseDTO;
import com.ufrn.imd.divide.ai.reform.model.Reform;
import org.springframework.stereotype.Component;

@Component
public class ReformMapper extends GroupMapper<Reform, ReformCreateRequestDTO, ReformUpdateRequestDTO, ReformResponseDTO> {
    public ReformMapper(UserMapper userMapper) {
        super(userMapper);
    }

    @Override
    public Reform toEntity(ReformCreateRequestDTO groupCreateRequestDTO) {
        if (groupCreateRequestDTO == null) {
            return null;
        }
        Reform reform = mapCreateFields(new Reform(), groupCreateRequestDTO);
        reform.setStartDate(groupCreateRequestDTO.getStartDate());
        reform.setLocal(groupCreateRequestDTO.getLocal());
        return reform;
    }

    @Override
    public Reform toEntity(ReformUpdateRequestDTO groupUpdateRequestDTO) {
        if (groupUpdateRequestDTO == null) {
            return null;
        }
        Reform reform = mapUpdateFields(new Reform(), groupUpdateRequestDTO);
        reform.setStartDate(groupUpdateRequestDTO.getStartDate());
        reform.setLocal(groupUpdateRequestDTO.getLocal());
        return reform;
    }

    @Override
    public ReformResponseDTO toDto(Reform group) {
        if (group == null) {
            return null;
        }
        ReformResponseDTO response = mapCommonGroupFields(group, new ReformResponseDTO());
        response.setStartDate(group.getStartDate());
        response.setLocal(group.getLocal());
        return response;
    }
}
