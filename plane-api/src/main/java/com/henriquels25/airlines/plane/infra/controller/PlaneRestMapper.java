package com.henriquels25.airlines.plane.infra.controller;

import com.henriquels25.airlines.plane.Plane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface PlaneRestMapper {
    Plane toPlane(PlaneDTO planeDTO);

    PlaneDTO toPlaneDTO(Plane planeDTO);
}
