package com.henriquels25.airlines.flight.infra.controller;

import com.henriquels25.airlines.airport.Airport;
import com.henriquels25.airlines.flight.Flight;
import com.henriquels25.airlines.flight.FlightOperations;
import com.henriquels25.airlines.plane.Plane;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.henriquels25.airlines.TestData.*;
import static com.henriquels25.airlines.flight.FlightStatus.CONFIRMED;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
@Import(FlightRestMapperImpl.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightOperations flightOperations;

    @Test
    public void shouldCreateAFlight() throws Exception {
        FlightDTO flightDTO = FlightDTO.builder()
                .planeId(PLANE_ID)
                .origin(POA_CODE)
                .destination(CNH_CODE)
                .build();

        Flight flight = Flight.builder()
                .plane(Plane.builder().id(PLANE_ID).build())
                .origin(Airport.builder().code(POA_CODE).build())
                .destination(Airport.builder().code(CNH_CODE).build())
                .build();

        when(flightOperations.create(flight)).thenReturn(FLIGHT_ID);

        mockMvc.perform(post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(flightDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/flights/" + FLIGHT_ID));

        verify(flightOperations).create(flight);
    }

    @Test
    void shouldFindFlightByIdWhenItExists() throws Exception {
        when(flightOperations.findById(FLIGHT_ID)).thenReturn(Optional.of(
                FLIGHT.toBuilder().plane(PLANE_WITH_ID).build()));

        mockMvc.perform(get("/flights/" + FLIGHT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("planeId").value(PLANE_ID))
                .andExpect(jsonPath("origin").value(POA_CODE))
                .andExpect(jsonPath("destination").value(CNH_CODE))
                .andExpect(jsonPath("status").value(CONFIRMED.toString()));

        verify(flightOperations).findById(FLIGHT_ID);
    }

    @Test
    void shouldReturn404WhenFlightDoesNotExist() throws Exception {
        when(flightOperations.findById(FLIGHT_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/flights/" + FLIGHT_ID))
                .andExpect(status().isNotFound());

        verify(flightOperations).findById(FLIGHT_ID);
    }

}