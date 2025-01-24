package com.ufrn.imd.divide.ai.trip.service;

import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import com.ufrn.imd.divide.ai.framework.service.DebtService;
import com.ufrn.imd.divide.ai.framework.service.GroupService;
import com.ufrn.imd.divide.ai.framework.service.UserService;
import com.ufrn.imd.divide.ai.framework.service.UserValidationService;
import com.ufrn.imd.divide.ai.trip.dto.request.TripCreateRequestDTO;
import com.ufrn.imd.divide.ai.trip.dto.request.TripUpdateRequestDTO;
import com.ufrn.imd.divide.ai.trip.dto.response.TripResponseDTO;
import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.trip.mapper.TripMapper;
import com.ufrn.imd.divide.ai.trip.model.Trip;
import com.ufrn.imd.divide.ai.trip.repository.TripRepository;
import com.ufrn.imd.divide.ai.trip.closure.DateEndStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Profile("trip")
@Service
public class TripService extends GroupService<Trip, TripRepository, TripCreateRequestDTO, TripUpdateRequestDTO, TripResponseDTO> {

    public TripService(TripRepository repository, TripMapper tripMapper,
                       @Lazy UserService userService, DebtService debtService,
                       UserValidationService userValidationService,
                       GroupTransactionRepository groupTransactionRepository,
                       DateEndStrategy dateEndStrategy) {
        super(repository, tripMapper, userService, debtService, userValidationService, groupTransactionRepository, dateEndStrategy);
    }

    @Override
    protected void validateBeforeSave(Trip group) {
        validateDate(group);
        validateOverlapping(group);
    }

    private void validateOverlapping(Trip trip) {
        List<Trip> overlappingTrips = repository.findByMemberAndDatesOverlapping(
                trip.getCreatedBy(),
                trip.getOccurrenceDate(),
                trip.getEndDate()
        );

        overlappingTrips = overlappingTrips.stream()
                .filter(other -> trip.getId() == null || !trip.getId().equals(other.getId())).toList();

        if (!overlappingTrips.isEmpty()) {
            Trip conflictingTrip = overlappingTrips.get(0);
            throw new BusinessException(
                    "O usuário está em uma viagem no período de " +
                            conflictingTrip.getOccurrenceDate() + " a " +
                            conflictingTrip.getEndDate() + ".",
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    private void validateDate(Trip trip) {
        if(trip.getOccurrenceDate().toLocalDate().isBefore(LocalDate.now()))  {
            throw new BusinessException(
                    "A data de início de uma viagem não pode ser anterior ao momento atual.",
                    HttpStatus.BAD_REQUEST
            );
        }

        if(trip.getEndDate().isBefore(trip.getOccurrenceDate()))  {
            throw new BusinessException(
                    "A data de início de uma viagem não pode ser posterior à data final.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
