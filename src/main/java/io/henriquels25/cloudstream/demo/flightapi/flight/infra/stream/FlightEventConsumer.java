package io.henriquels25.cloudstream.demo.flightapi.flight.infra.stream;

import io.henriquels25.cloudstream.demo.flightapi.airport.Airport;
import io.henriquels25.cloudstream.demo.flightapi.flight.FlightOperations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("flightEventConsumer")
@AllArgsConstructor
class FlightEventConsumer implements Consumer<FlightEvent> {

    private final FlightOperations flightOperations;

    @Override
    public void accept(FlightEvent flightEvent) {
        Airport airport = new Airport(flightEvent.getCurrentAirport());
        flightOperations.flightArrivedIn(flightEvent.getFlightId(), airport);
    }
}
