package com.henriquels25.airlines.plane.infra.stream;

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
