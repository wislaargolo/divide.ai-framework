package com.ufrn.imd.divide.ai.trip.repository;

import com.ufrn.imd.divide.ai.framework.repository.GroupRepository;
import com.ufrn.imd.divide.ai.trip.model.Trip;
import com.ufrn.imd.divide.ai.framework.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends GroupRepository<Trip> {
    @Query("SELECT t FROM Trip t " +
            "JOIN t.members m " +
            "WHERE m = :member " +
            "AND DATE(t.occurrenceDate) < DATE(:endDate) " +
            "AND DATE(t.endDate) > DATE(:startDate)")
    List<Trip> findByMemberAndDatesOverlapping(@Param("member") User member,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);
}
