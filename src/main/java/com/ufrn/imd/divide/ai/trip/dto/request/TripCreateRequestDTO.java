package com.ufrn.imd.divide.ai.trip.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TripCreateRequestDTO extends GroupCreateRequestDTO {

    @NotNull(message = "startDate é obrigatório.")
    private LocalDate startDate;
    @NotBlank(message = "destination é obrigatório.")
    private String destination;

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
