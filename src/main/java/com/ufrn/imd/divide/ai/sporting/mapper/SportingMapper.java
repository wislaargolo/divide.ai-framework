package com.ufrn.imd.divide.ai.sporting.mapper;

import com.ufrn.imd.divide.ai.framework.mapper.GroupMapper;
import com.ufrn.imd.divide.ai.framework.mapper.UserMapper;
import com.ufrn.imd.divide.ai.sporting.dto.request.SportingCreateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.dto.request.SportingUpdateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.dto.response.SportingResponseDTO;
import com.ufrn.imd.divide.ai.sporting.model.Sporting;
import org.springframework.stereotype.Component;

@Component
public class SportingMapper extends GroupMapper<Sporting, SportingCreateRequestDTO, SportingUpdateRequestDTO, SportingResponseDTO> {
    public SportingMapper(UserMapper userMapper) {
        super(userMapper);
    }

    @Override
    public Sporting toEntity(SportingCreateRequestDTO sportingCreateRequestDTO) {
        if (sportingCreateRequestDTO == null) {
            return null;
        }
        Sporting sporting = mapCreateFields(new Sporting(), sportingCreateRequestDTO);

        sporting.setLocal(sportingCreateRequestDTO.getLocal());
        sporting.setSportingsModalities(sportingCreateRequestDTO.getSportingsModalities());
        return sporting;
    }

    @Override
    public Sporting toEntity(SportingUpdateRequestDTO sportingUpdateRequestDTO) {
        if (sportingUpdateRequestDTO == null) {
            return null;
        }
        Sporting sporting = mapUpdateFields(new Sporting(), sportingUpdateRequestDTO);

        sporting.setLocal(sportingUpdateRequestDTO.getLocal());
        sporting.setSportingsModalities(sportingUpdateRequestDTO.getSportingsModalities());
        return sporting;
    }
    @Override
    public SportingResponseDTO toDto(Sporting sporting) {
        if (sporting == null) {
            return null;
        }
        SportingResponseDTO response = mapCommonGroupFields(sporting, new SportingResponseDTO());

        response.setLocal(sporting.getLocal());
        response.setSportingsModalities(sporting.getSportingsModalities());
        return response;
    }


}
