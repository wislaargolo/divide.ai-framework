package com.ufrn.imd.divide.ai.trip.dto.response;

import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;

import java.time.LocalDate;

public class TripResponseDTO extends GroupResponseDTO {

    private LocalDate startDate;
    private String destination;

    public TripResponseDTO() {}

    public TripResponseDTO(Long id, String name) {
        super(id, name);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
