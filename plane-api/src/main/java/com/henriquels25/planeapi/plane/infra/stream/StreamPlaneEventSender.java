package com.henriquels25.planeapi.plane.infra.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamPlaneEventSender implements PlaneArrivedSender {

    private final StreamBridge streamBridge;

    @Override
    public void send(PlaneArrived planeEvent) {
        streamBridge.send("planeArrived-out-0", planeEvent);
    }
}
