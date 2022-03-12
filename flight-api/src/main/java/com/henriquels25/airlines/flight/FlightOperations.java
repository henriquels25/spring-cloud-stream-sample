package com.henriquels25.airlines.flight;

import com.henriquels25.airlines.airport.Airport;

import java.util.Optional;

public interface FlightOperations {
    String create(Flight flight);

    Optional<Flight> findById(String id);

    Optional<Flight> findConfirmedFlightByPlaneId(String planeId);

    void flightArrivedIn(String flightId, Airport airport);
}
