package io.henriquels25.cloudstream.demo.flightapi.plane.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.plane.Plane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface PlaneMapper {

    PlaneDocument toPlaneDocument(Plane plane);
}
