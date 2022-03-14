package com.henriquels25.flightapi.plane.infra.stream;

import com.henriquels25.flightapi.flight.Flight;
import com.henriquels25.flightapi.flight.FlightOperations;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static org.springframework.kafka.support.KafkaHeaders.MESSAGE_KEY;

@Component("planeEventProcessor")
@AllArgsConstructor
class PlaneEventProcessor implements Function<Message<PlaneEvent>, Message<FlightEvent>> {

    private final FlightOperations flightOperations;

    @Override
    public Message<FlightEvent> apply(Message<PlaneEvent> planeEvent) {
        PlaneEvent payload = planeEvent.getPayload();
        String planeId = payload.getPlaneId();
        Flight flight = flightOperations.findConfirmedFlightByPlaneId(planeId)
                .orElseThrow(() ->
                        new NoFlightFoundException(
                                String.format("No flight found for plane id %s", planeId)));
        return MessageBuilder.withPayload(new FlightEvent(flight.getId(),
                        payload.getCurrentAirport()))
                .setHeader(MESSAGE_KEY, flight.getId())
                .build();
    }
}
