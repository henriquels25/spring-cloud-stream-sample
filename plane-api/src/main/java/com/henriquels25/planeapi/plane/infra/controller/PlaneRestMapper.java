package com.henriquels25.planeapi.plane.infra.controller;

import com.henriquels25.planeapi.plane.Plane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface PlaneRestMapper {
    Plane toPlane(PlaneDTO planeDTO);

    PlaneDTO toPlaneDTO(Plane planeDTO);
}
