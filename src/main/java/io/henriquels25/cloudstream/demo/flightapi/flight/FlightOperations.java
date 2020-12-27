package io.henriquels25.cloudstream.demo.flightapi.flight;

import java.util.Optional;

public interface FlightOperations {
    String create(Flight flight);

    Optional<Flight> findConfirmedFlightByPlaneId(String planeId);
}
