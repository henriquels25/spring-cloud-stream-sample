package com.henriquels25.airlines.flight.infra.stream;

import com.henriquels25.airlines.airport.Airport;
import com.henriquels25.airlines.flight.FlightOperations;
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
