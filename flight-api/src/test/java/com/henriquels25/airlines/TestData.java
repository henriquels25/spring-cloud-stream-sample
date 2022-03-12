package com.henriquels25.airlines;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriquels25.airlines.airport.Airport;
import com.henriquels25.airlines.flight.Flight;
import com.henriquels25.airlines.flight.FlightStatus;
import com.henriquels25.airlines.plane.Plane;
import lombok.SneakyThrows;

public class TestData {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String CODE = "PR-MYK";
    public static final String TYPE = "Airbus A320";
    public static final String PLANE_ID = "idPlane";
    public static final String PLANE_ID_2 = "idPlane2";
    public static final String FLIGHT_ID = "flightId1";

    public static final String FLIGHT_ID_2 = "flightId2";


    public static final Plane PLANE = Plane.builder().code(CODE).type(TYPE).build();
    public static final Plane PLANE_WITH_ID = Plane.builder().id(PLANE_ID).code(CODE).type(TYPE).build();
    public static final Plane PLANE_WITH_ID_2 = Plane.builder().id(PLANE_ID_2).code(CODE).type(TYPE).build();

    public static final String POA_CODE = "POA";
    public static final String CNH_CODE = "CNH";
    public static final Airport POA = new Airport(POA_CODE);
    public static final Airport CNH = new Airport(CNH_CODE);

    public static final Flight FLIGHT = Flight.builder().plane(PLANE).origin(POA)
            .destination(CNH)
            .status(FlightStatus.CONFIRMED).build();

    public static final Flight FLIGHT_WITH_ID = Flight.builder()
            .id(FLIGHT_ID)
            .plane(PLANE_WITH_ID).origin(POA)
            .destination(CNH)
            .status(FlightStatus.CONFIRMED).build();

    public static final Flight FLIGHT_WITH_ID_2 = Flight.builder()
            .id(FLIGHT_ID_2)
            .plane(PLANE_WITH_ID).origin(CNH)
            .destination(POA)
            .status(FlightStatus.CONFIRMED).build();

    @SneakyThrows
    public static String toJson(Object object) {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

}
