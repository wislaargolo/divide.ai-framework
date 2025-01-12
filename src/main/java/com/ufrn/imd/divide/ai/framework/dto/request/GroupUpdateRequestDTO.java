package com.ufrn.imd.divide.ai.framework.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GroupUpdateRequestDTO {

    private String name;
    private String description;
    private LocalDateTime occurrenceDate;

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

    public LocalDateTime getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(LocalDateTime occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }
}
