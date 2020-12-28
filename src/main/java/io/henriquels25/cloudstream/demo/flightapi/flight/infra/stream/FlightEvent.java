package io.henriquels25.cloudstream.demo.flightapi.flight.infra.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
class FlightEvent {
    private String flightId;
    private String currentAirport;
}
