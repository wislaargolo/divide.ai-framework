package com.ufrn.imd.divide.ai.trip.repository;

import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.trip.model.Trip;
import com.ufrn.imd.divide.ai.framework.model.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TripRepository extends GroupRepository<Trip> {
    Optional<Trip> findByDestinationAndStartDateAndMembersContains(String destination, LocalDate startDate, User user);
}
