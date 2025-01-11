package com.ufrn.imd.divide.ai.trip.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TripCreateRequestDTO extends GroupCreateRequestDTO {

    @NotBlank(message = "destination é obrigatório.")
    private String destination;
    @NotNull(message = "endDate é obrigatório.")
    private LocalDate endDate;

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
