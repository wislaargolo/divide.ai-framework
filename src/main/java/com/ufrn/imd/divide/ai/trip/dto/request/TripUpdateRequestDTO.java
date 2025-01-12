package com.ufrn.imd.divide.ai.trip.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripUpdateRequestDTO extends GroupUpdateRequestDTO {

    private String destination;
    private LocalDateTime endDate;

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
