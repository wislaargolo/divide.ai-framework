package com.ufrn.imd.divide.ai.trip.dto.response;

import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripResponseDTO extends GroupResponseDTO {

    private String destination;
    private LocalDateTime endDate;

    public TripResponseDTO() {}

    public TripResponseDTO(Long id, String name) {
        super(id, name);
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
