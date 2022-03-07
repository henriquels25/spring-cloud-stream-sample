package com.henriquels25.airlines.plane.infra.controller;

import com.henriquels25.airlines.plane.Plane;
import com.henriquels25.airlines.plane.Plane.PlaneBuilder;
import com.henriquels25.airlines.plane.infra.controller.PlaneDTO.PlaneDTOBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-06T20:01:34-0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
class PlaneRestMapperImpl implements PlaneRestMapper {

    @Override
    public Plane toPlane(PlaneDTO planeDTO) {
        if ( planeDTO == null ) {
            return null;
        }

        PlaneBuilder plane = Plane.builder();

        plane.code( planeDTO.getCode() );
        plane.type( planeDTO.getType() );

        return plane.build();
    }

    @Override
    public PlaneDTO toPlaneDTO(Plane planeDTO) {
        if ( planeDTO == null ) {
            return null;
        }

        PlaneDTOBuilder planeDTO1 = PlaneDTO.builder();

        planeDTO1.code( planeDTO.getCode() );
        planeDTO1.type( planeDTO.getType() );

        return planeDTO1.build();
    }
}
