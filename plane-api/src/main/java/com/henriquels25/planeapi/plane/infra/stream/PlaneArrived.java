package com.henriquels25.planeapi.plane.infra.stream;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class PlaneArrived {
    private String planeId;
    private String currentAirport;
}
