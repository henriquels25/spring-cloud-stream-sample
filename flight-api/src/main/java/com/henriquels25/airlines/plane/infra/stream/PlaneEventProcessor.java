package com.henriquels25.airlines.plane.infra.stream;

import com.henriquels25.airlines.flight.Flight;
import com.henriquels25.airlines.flight.FlightOperations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("planeEventProcessor")
@AllArgsConstructor
class PlaneEventProcessor implements Function<PlaneEvent, FlightEvent> {

    private final FlightOperations flightOperations;

    @Override
    public FlightEvent apply(PlaneEvent planeEvent) {
        String planeId = planeEvent.getPlaneId();
        Flight flight = flightOperations.findConfirmedFlightByPlaneId(planeId)
                .orElseThrow(() -> new NoFlightFoundException(String.format("No flight found for plane id %s", planeId)));
        return new FlightEvent(flight.getId(), planeEvent.getCurrentAirport());
    }
}
