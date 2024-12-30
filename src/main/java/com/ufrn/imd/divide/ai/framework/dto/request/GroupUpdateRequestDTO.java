package com.ufrn.imd.divide.ai.framework.dto.request;

import java.time.LocalDate;

public class GroupUpdateRequestDTO {

    private String name;
    private String description;
    private LocalDate finalOccurrenceDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFinalOccurrenceDate() {
        return finalOccurrenceDate;
    }

    public void setFinalOccurrenceDate(LocalDate finalOccurrenceDate) {
        this.finalOccurrenceDate = finalOccurrenceDate;
    }
}
