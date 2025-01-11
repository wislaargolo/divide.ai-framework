package com.ufrn.imd.divide.ai.trip.dto.response;

import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;

import java.time.LocalDate;

public class TripResponseDTO extends GroupResponseDTO {

    private String destination;
    private LocalDate endDate;

    public TripResponseDTO() {}

    public TripResponseDTO(Long id, String name) {
        super(id, name);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
