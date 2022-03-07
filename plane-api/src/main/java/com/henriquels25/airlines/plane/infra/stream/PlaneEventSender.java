package com.henriquels25.airlines.plane.infra.stream;

public interface PlaneEventSender {

    void send(PlaneEvent planeEvent);

}
