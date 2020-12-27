package io.henriquels25.cloudstream.demo.flightapi.flight;

import java.util.List;

public interface FlightRepository {
    String save(Flight flight);

    List<Flight> findConfirmedFlightsByPlaneId(String planeId);
}
