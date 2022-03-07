package com.henriquels25.airlines.plane.infra.stream;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class PlaneEvent {
    private String planeId;
    private String currentAirport;
}
