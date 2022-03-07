package com.henriquels25.airlines.plane.infra.mongo;

import com.henriquels25.airlines.plane.Plane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface PlaneMapper {

    PlaneDocument toPlaneDocument(Plane plane);

    Plane toPlane(PlaneDocument plane);
}
