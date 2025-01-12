package com.ufrn.imd.divide.ai.trip.model;

import com.ufrn.imd.divide.ai.framework.model.Group;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip extends Group {
    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime endDate;


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
