package com.henriquels25.flightapi.flight.infra.stream.consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
class FlightArrived {
    private String flightId;
    private String currentAirport;
}
