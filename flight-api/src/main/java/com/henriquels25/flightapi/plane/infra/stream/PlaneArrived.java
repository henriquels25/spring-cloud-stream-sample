package com.henriquels25.flightapi.plane.infra.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
class PlaneArrived {
    private String planeId;
    private String currentAirport;
}
