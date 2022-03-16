package com.henriquels25.planeapi.plane.infra.stream;

public interface PlaneArrivedSender {

    void send(PlaneArrived planeEvent);

}
