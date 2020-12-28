package io.henriquels25.cloudstream.demo.flightapi.flight;

import io.henriquels25.cloudstream.demo.flightapi.airport.Airport;

import java.util.Optional;

public interface FlightOperations {
    String create(Flight flight);

    Optional<Flight> findConfirmedFlightByPlaneId(String planeId);

    void flightArrivedIn(String flightId, Airport airport);
}
