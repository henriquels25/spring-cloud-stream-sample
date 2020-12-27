package io.henriquels25.cloudstream.demo.flightapi.flight;

import io.henriquels25.cloudstream.demo.flightapi.airport.Airport;
import io.henriquels25.cloudstream.demo.flightapi.plane.Plane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Flight {
    private final String id;
    private final Plane plane;
    private final Airport origin;
    private final Airport destination;
    private final FlightStatus status;
}
