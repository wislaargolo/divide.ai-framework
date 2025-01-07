package com.ufrn.imd.divide.ai.trip.dto.response;

import java.util.List;

public record TripOpenAIResponseDTO(
        String title,
        List<String> recomendations,
        List<Hosting> hosting,
        List<String> restaurants,
        DailyExpenseEstimate dailyExpenseEstimate,
        TotalExpenseEstimate totalExpenseEstimate
) {

    public record Hosting(
            String hotel,
            String pricePerNight,
            int numberOfParticipants
    ) {}

    public record DailyExpenseEstimate(
            String accommodation,
            String meals,
            String transportation,
            String activities
    ) {}

    public record TotalExpenseEstimate(
            TotalDays totalDays,
            String totalPerPerson
    ) {

        public record TotalDays(
                String accommodation,
                String meals,
                String transportation,
                String activities
        ) {}
    }
}

