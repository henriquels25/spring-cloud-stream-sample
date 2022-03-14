package com.henriquels25.planeapi.plane.infra.controller;

import com.henriquels25.planeapi.airport.Airport;
import com.henriquels25.planeapi.plane.PlaneOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight-arrivals")
public class FlightArrivalController {

    private final PlaneOperations planeOperations;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void flightArrived(@RequestBody FlightArrival flightArrival) {
        planeOperations.arrivedIn(flightArrival.getPlaneId(),
                new Airport(flightArrival.getAirport()));
    }

}
