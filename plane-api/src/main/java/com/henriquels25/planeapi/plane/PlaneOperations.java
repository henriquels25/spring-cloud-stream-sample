package com.henriquels25.planeapi.plane;

import com.henriquels25.planeapi.airport.Airport;

import java.util.Optional;

public interface PlaneOperations {
    String create(Plane plane);

    Optional<Plane> findById(String id);

    void arrivedIn(String planeId, Airport airport);
}
