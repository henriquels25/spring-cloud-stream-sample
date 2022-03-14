package com.henriquels25.planeapi.plane.infra.controller;

import com.henriquels25.planeapi.plane.PlaneOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/planes")
@RequiredArgsConstructor
public class PlaneController {

    private final PlaneOperations planeOperations;
    private final PlaneRestMapper planeMapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid PlaneDTO plane) {
        String createdId = planeOperations.create(planeMapper.toPlane(plane));
        return ResponseEntity.created(URI.create(String.format("/planes/%s", createdId))).build();
    }

    @GetMapping("/{id}")
    public PlaneDTO findById(@PathVariable String id) {
        return planeOperations.findById(id)
                .map(planeMapper::toPlaneDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
