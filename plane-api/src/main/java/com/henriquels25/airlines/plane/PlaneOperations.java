package com.henriquels25.airlines.plane;

import com.henriquels25.airlines.airport.Airport;

import java.util.Optional;

public interface PlaneOperations {
    String create(Plane plane);

    Optional<Plane> findById(String id);

    void arrivedIn(String planeId, Airport airport);
}
