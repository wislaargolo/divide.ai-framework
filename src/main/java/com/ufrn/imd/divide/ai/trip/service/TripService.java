package com.ufrn.imd.divide.ai.trip.service;

import com.ufrn.imd.divide.ai.framework.model.User;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;

@Service
public class TripService extends GroupService<Trip, TripCreateRequestDTO, TripUpdateRequestDTO, TripResponseDTO> {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository, TripMapper tripMapper,
                       @Lazy UserService userService, DebtService debtService,
                       UserValidationService userValidationService,
                       GroupTransactionRepository groupTransactionRepository,
                       DateEndStrategy dateEndStrategy) {
        super(tripRepository, tripMapper, userService, debtService, userValidationService, groupTransactionRepository, dateEndStrategy);
        this.tripRepository = tripRepository;
    }

    @Override
    protected void validateBeforeSave(Trip group) {
        validateDate(group);
        validateTrip(group);
    }

    private void validateTrip(Trip trip) {
        Optional<Trip> other = tripRepository.findByDestinationAndOccurrenceDateAndMembersContains(trip.getDestination(), trip.getOccurrenceDate(), trip.getCreatedBy());
        if(other.isPresent() && (trip.getId() == null || !trip.getId().equals(other.get().getId()))) {
            throw new BusinessException(
                    "Já existe uma viagem com o destino " + trip.getDestination() +
                            " e data de início " + trip.getOccurrenceDate() + " em que você é membro.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validateDate(Trip trip) {
        if(trip.getOccurrenceDate().isBefore(LocalDate.now()))  {
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
