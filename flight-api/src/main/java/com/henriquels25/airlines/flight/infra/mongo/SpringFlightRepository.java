package com.henriquels25.airlines.flight.infra.mongo;

import com.henriquels25.airlines.flight.FlightStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface SpringFlightRepository extends MongoRepository<FlightDocument, String> {
    List<FlightDocument> findByPlaneIdAndStatus(String planeId, FlightStatus status);
}
