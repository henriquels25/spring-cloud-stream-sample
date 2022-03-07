package com.henriquels25.airlines.plane.infra.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamPlaneEventSender implements PlaneEventSender {

    private final StreamBridge streamBridge;

    @Override
    public void send(PlaneEvent planeEvent) {
        streamBridge.send("planeEvent-out-0", planeEvent);
    }
}
