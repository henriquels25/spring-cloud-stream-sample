package com.henriquels25.flightapi.flight.infra.controller;

import com.henriquels25.flightapi.flight.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightRestMapper {
    @Mapping(source = "plane.id", target = "planeId")
    @Mapping(source = "origin.code", target = "origin")
    @Mapping(source = "destination.code", target = "destination")
    FlightDTO toFlightDTO(Flight flight);

    @Mapping(source = "planeId", target = "plane.id")
    @Mapping(source = "origin", target = "origin.code")
    @Mapping(source = "destination", target = "destination.code")
    Flight fromFlightDTO(FlightDTO flight);
}
