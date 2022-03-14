package com.henriquels25.planeapi.plane.infra.stream;

public interface PlaneEventSender {

    void send(PlaneEvent planeEvent);

}
