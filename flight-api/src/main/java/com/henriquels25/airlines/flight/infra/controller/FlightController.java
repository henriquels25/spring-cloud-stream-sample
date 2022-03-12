package com.henriquels25.airlines.flight.infra.controller;

import com.henriquels25.airlines.flight.FlightOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightOperations flightOperations;
    private final FlightRestMapper flightMapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody FlightDTO flight) {
        String id = flightOperations.create(flightMapper.fromFlightDTO(flight));
        return ResponseEntity.created(URI.create(String.format("/flights/%s", id))).build();
    }

    @GetMapping("/{id}")
    public FlightDTO getById(@PathVariable String id) {
        return flightOperations.findById(id)
                .map(flightMapper::toFlightDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

}
