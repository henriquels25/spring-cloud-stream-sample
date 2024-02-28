package com.henriquels25.flightapi.plane.infra.stream;

import com.henriquels25.flightapi.flight.Flight;
import com.henriquels25.flightapi.flight.FlightOperations;
import lombok.AllArgsConstructor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("planeArrivedProcessor")
@AllArgsConstructor
class PlaneArrivedProcessor implements Function<PlaneArrived, Message<FlightArrived>> {

    private final FlightOperations flightOperations;

    @Override
    public Message<FlightArrived> apply(PlaneArrived planeEvent) {
        String planeId = planeEvent.getPlaneId();
        Flight flight = flightOperations.findConfirmedFlightByPlaneId(planeId)
                .orElseThrow(() -> new NoFlightFoundException(String.format("No flight found for plane id %s", planeId)));
        return MessageBuilder.withPayload(new FlightArrived(flight.getId(), planeEvent.getCurrentAirport()))
                .setHeader(KafkaHeaders.KEY, flight.getId()).build();
    }
}
