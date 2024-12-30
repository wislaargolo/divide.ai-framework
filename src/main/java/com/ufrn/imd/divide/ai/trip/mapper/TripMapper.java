package com.ufrn.imd.divide.ai.trip.mapper;

import com.ufrn.imd.divide.ai.framework.mapper.GroupMapper;
import com.ufrn.imd.divide.ai.framework.mapper.UserMapper;
import com.ufrn.imd.divide.ai.trip.dto.request.TripCreateRequestDTO;
import com.ufrn.imd.divide.ai.trip.dto.request.TripUpdateRequestDTO;
import com.ufrn.imd.divide.ai.trip.dto.response.TripResponseDTO;
import com.ufrn.imd.divide.ai.trip.model.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripMapper extends GroupMapper<Trip, TripCreateRequestDTO, TripUpdateRequestDTO, TripResponseDTO> {

    public TripMapper(UserMapper userMapper) {
        super(userMapper);
    }

    @Override
    public Trip toEntity(TripCreateRequestDTO groupCreateRequestDTO) {
        if (groupCreateRequestDTO == null) {
            return null;
        }
        Trip trip = mapCreateFields(new Trip(), groupCreateRequestDTO);
        trip.setStartDate(groupCreateRequestDTO.getStartDate());
        trip.setDestination(groupCreateRequestDTO.getDestination());
        return trip;
    }

    @Override
    public Trip toEntity(TripUpdateRequestDTO groupUpdateRequestDTO) {
        if (groupUpdateRequestDTO == null) {
            return null;
        }
        Trip trip = mapUpdateFields(new Trip(), groupUpdateRequestDTO);
        trip.setStartDate(groupUpdateRequestDTO.getStartDate());
        trip.setDestination(groupUpdateRequestDTO.getDestination());
        return trip;
    }

    @Override
    public TripResponseDTO toDto(Trip group) {
        if (group == null) {
            return null;
        }
        TripResponseDTO response = mapCommonGroupFields(group, new TripResponseDTO());
        response.setStartDate(group.getStartDate());
        response.setDestination(group.getDestination());
        return response;
    }
}
