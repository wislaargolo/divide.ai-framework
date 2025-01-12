package com.ufrn.imd.divide.ai.trip.controller;

import com.ufrn.imd.divide.ai.framework.controller.GroupController;
import com.ufrn.imd.divide.ai.trip.dto.request.TripCreateRequestDTO;
import com.ufrn.imd.divide.ai.trip.dto.request.TripUpdateRequestDTO;
import com.ufrn.imd.divide.ai.trip.dto.response.TripResponseDTO;
import com.ufrn.imd.divide.ai.trip.model.Trip;
import com.ufrn.imd.divide.ai.trip.service.TripService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("trip")
@RestController
@RequestMapping("/trips")
public class TripController extends GroupController<Trip, TripCreateRequestDTO, TripUpdateRequestDTO, TripResponseDTO> {
    public TripController(TripService tripService) {
        super(tripService);
    }
}
