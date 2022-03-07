package com.henriquels25.airlines.plane.infra.controller;

import com.henriquels25.airlines.plane.PlaneOperations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.henriquels25.airlines.plane.TestData.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlaneController.class)
@Import(PlaneRestMapperImpl.class)
class PlaneControllerTest {

    @MockBean
    private PlaneOperations planeOperations;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        when(planeOperations.create(PLANE)).thenReturn(PLANE_ID);

        mockMvc.perform(post("/planes").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(PLANE)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/planes/" + PLANE_ID));

        verify(planeOperations).create(PLANE);
    }

    @Test
    void findByIdWhenPlaneExists() throws Exception {
        when(planeOperations.findById(PLANE_ID)).thenReturn(Optional.of(PLANE));

        mockMvc.perform(get("/planes/" + PLANE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(PLANE.getCode()))
                .andExpect(jsonPath("type").value(PLANE.getType()));

        verify(planeOperations).findById(PLANE_ID);
    }

    @Test
    void findByIdWhenPlaneDoesNotExist() throws Exception {
        when(planeOperations.findById(PLANE_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/planes/" + PLANE_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());

        verify(planeOperations).findById(PLANE_ID);
    }
}