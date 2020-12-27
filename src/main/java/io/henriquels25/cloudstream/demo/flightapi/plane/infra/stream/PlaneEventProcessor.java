package io.henriquels25.cloudstream.demo.flightapi.plane.infra.stream;

import io.henriquels25.cloudstream.demo.flightapi.flight.Flight;
import io.henriquels25.cloudstream.demo.flightapi.flight.FlightOperations;
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
                .orElseThrow(() -> new NoFlightFoundException(String.format("No flight found for plane id %s".format(planeId))));
        return new FlightEvent(flight.getId(), planeEvent.getCurrentAirport());
    }
}
