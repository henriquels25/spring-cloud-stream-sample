package com.henriquels25.flightapi.plane.infra.stream;

import com.henriquels25.flightapi.flight.Flight;
import com.henriquels25.flightapi.flight.FlightOperations;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("planeEventProcessor")
@AllArgsConstructor
class PlaneEventProcessor implements Function<Message<PlaneEvent>, Message<FlightEvent>> {

    private final FlightOperations flightOperations;

    @Override
    public Message<FlightEvent> apply(Message<PlaneEvent> planeEvent) {
        String planeId = planeEvent.getPayload().getPlaneId();
        Flight flight = flightOperations.findConfirmedFlightByPlaneId(planeId)
                .orElseThrow(() -> new NoFlightFoundException(String.format("No flight found for plane id %s", planeId)));
        return new GenericMessage<>(new FlightEvent(flight.getId(),
                planeEvent.getPayload().getCurrentAirport()));
    }
}
