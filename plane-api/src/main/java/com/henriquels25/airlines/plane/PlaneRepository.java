package com.henriquels25.airlines.plane;

import java.util.Optional;

public interface PlaneRepository {
    String save(Plane plane);

    Optional<Plane> findById(String id);
}
