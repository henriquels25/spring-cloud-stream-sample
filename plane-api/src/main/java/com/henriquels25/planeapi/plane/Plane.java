package com.henriquels25.planeapi.plane;

import com.henriquels25.planeapi.airport.Airport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Plane {
    private final String id;
    private final String code;
    private final String type;
    private final Airport airport;
}
