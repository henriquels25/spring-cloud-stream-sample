package com.henriquels25.flightapi.flight.infra.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
class FlightArrivedEvent {
    private String flightId;
}
