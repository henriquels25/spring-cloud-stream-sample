package com.henriquels25.flightapi.flight.infra.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.henriquels25.flightapi.flight.FlightStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
class FlightDTO {

    private final String planeId;
    private final String origin;
    private final String destination;
    private final FlightStatus status;
}
