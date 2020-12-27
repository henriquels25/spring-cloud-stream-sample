package io.henriquels25.cloudstream.demo.flightapi.plane.infra.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
class PlaneEvent {
    private String planeId;
    private String currentAirport;
}
