package com.henriquels25.flightapi.plane;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Plane {
    private final String id;
    private final String code;
    private final String type;
}
