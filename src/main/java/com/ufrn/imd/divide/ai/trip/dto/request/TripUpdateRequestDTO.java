package com.ufrn.imd.divide.ai.trip.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;

import java.time.LocalDate;

public class TripUpdateRequestDTO extends GroupUpdateRequestDTO {

    private LocalDate startDate;
    private String destination;
    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
