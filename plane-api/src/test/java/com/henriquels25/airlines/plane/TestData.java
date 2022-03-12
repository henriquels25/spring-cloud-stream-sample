package com.henriquels25.airlines.plane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriquels25.airlines.airport.Airport;
import com.henriquels25.airlines.plane.infra.stream.PlaneEvent;
import lombok.SneakyThrows;

public class TestData {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String CODE = "PR-MYK";
    public static final String TYPE = "Airbus A320";
    public static final String PLANE_ID = "idPlane";
    public static final String PLANE_ID_2 = "idPlane2";
    public static final String FLIGHT_ID = "flightId1";

    public static final String FLIGHT_ID_2 = "flightId2";

    public static final String POA_CODE = "POA";
    public static final String CNH_CODE = "CNH";

    public static final Airport POA_AIRPORT = new Airport(POA_CODE);
    public static final Airport CNH_AIRPORT = new Airport(CNH_CODE);

    public static final Plane PLANE = Plane.builder().code(CODE).type(TYPE).airport(POA_AIRPORT).build();
    public static final Plane PLANE_WITH_ID = Plane.builder().id(PLANE_ID).code(CODE).type(TYPE).build();
    public static final Plane PLANE_WITH_ID_2 = Plane.builder().id(PLANE_ID_2).code(CODE).type(TYPE).build();

    public static final PlaneEvent PLANE_EVENT = PlaneEvent.builder()
            .planeId(PLANE_ID)
            .currentAirport(POA_CODE)
            .build();

    @SneakyThrows
    public static String toJson(Plane plane) {
        return OBJECT_MAPPER.writeValueAsString(plane);
    }

}
