package com.ufrn.imd.divide.ai.trip.model;

import com.ufrn.imd.divide.ai.framework.model.Group;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "trips")
public class Trip extends Group {
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
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
