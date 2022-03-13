package com.henriquels25.flightapi.flight.infra.stream;

import com.henriquels25.flightapi.flight.FlightNotifications;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class StreamFlightNotifications implements FlightNotifications {

    private final StreamBridge streamBridge;

    @Override
    public void flightArrived(String flightId) {
        streamBridge.send("flightArrived-out-0", new FlightArrivedEvent(flightId));
    }
}
