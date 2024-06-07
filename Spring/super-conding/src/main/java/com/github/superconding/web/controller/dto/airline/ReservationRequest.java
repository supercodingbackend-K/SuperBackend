package com.github.superconding.web.controller.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class ReservationRequest {
    private Integer userId;
    private Integer airlineTicketId;

    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
        return null;
    }
}