package com.henriquels25.flightapi.flight.infra.stream.consumer;

import com.henriquels25.flightapi.airport.Airport;
import com.henriquels25.flightapi.flight.FlightOperations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("flightArrivedConsumer")
@AllArgsConstructor
class FlightArrivedConsumer implements Consumer<FlightArrived> {

    private final FlightOperations flightOperations;

    @Override
    public void accept(FlightArrived flightEvent) {
        Airport airport = new Airport(flightEvent.getCurrentAirport());
        flightOperations.flightArrivedIn(flightEvent.getFlightId(), airport);
    }
}
