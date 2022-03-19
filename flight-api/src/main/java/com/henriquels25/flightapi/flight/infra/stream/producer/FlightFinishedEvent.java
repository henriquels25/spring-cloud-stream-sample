package com.henriquels25.flightapi.flight.infra.stream.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
class FlightFinishedEvent {
    private String flightId;
}
