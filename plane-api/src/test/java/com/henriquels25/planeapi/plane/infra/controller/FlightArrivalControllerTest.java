package com.henriquels25.planeapi.plane.infra.controller;

import com.henriquels25.planeapi.plane.PlaneNotFoundException;
import com.henriquels25.planeapi.plane.PlaneOperations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.henriquels25.planeapi.plane.TestData.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.integration.json.SimpleJsonSerializer.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightArrivalController.class)
class FlightArrivalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaneOperations planeOperations;

    @Test
    void flighArrivedTest() throws Exception {
        FlightArrival flightArrival = FlightArrival.builder()
                .airport(POA_CODE)
                .planeId(PLANE_ID).build();

        mockMvc.perform(post("/flight-arrivals").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(flightArrival)))
                .andExpect(status().isCreated());

        verify(planeOperations).arrivedIn(PLANE_ID, POA_AIRPORT);
    }

    @Test
    void shouldReturnBadRequestWhenPlaneDoesNotExist() throws Exception {
        doThrow(PlaneNotFoundException.class).when(planeOperations).arrivedIn(PLANE_ID, POA_AIRPORT);

        FlightArrival flightArrival = FlightArrival.builder()
                .airport(POA_CODE)
                .planeId(PLANE_ID).build();

        mockMvc.perform(post("/flight-arrivals").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(flightArrival)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Plane not found"));

        verify(planeOperations).arrivedIn(PLANE_ID, POA_AIRPORT);
    }

}