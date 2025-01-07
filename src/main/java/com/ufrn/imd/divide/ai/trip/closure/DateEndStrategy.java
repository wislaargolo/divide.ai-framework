package com.ufrn.imd.divide.ai.trip.closure;

import com.ufrn.imd.divide.ai.framework.closure.GroupClosureStrategy;
import com.ufrn.imd.divide.ai.trip.model.Trip;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateEndStrategy implements GroupClosureStrategy<Trip> {
    @Override
    public boolean shouldCloseGroup(Trip group) {
        LocalDate now = LocalDate.now();
        return now.isAfter(group.getFinalOccurrenceDate());
    }
}
