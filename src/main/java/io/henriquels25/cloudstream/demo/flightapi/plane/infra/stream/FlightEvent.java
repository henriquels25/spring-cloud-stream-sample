package io.henriquels25.cloudstream.demo.flightapi.plane.infra.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class FlightEvent {
    private final String flightId;
    private final String currentAirport;
}
