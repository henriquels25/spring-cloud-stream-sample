package io.henriquels25.cloudstream.demo.flightapi.flight.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.flight.FlightStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface SpringFlightRepository extends MongoRepository<FlightDocument, String> {
    List<FlightDocument> findByPlaneIdAndStatus(String planeId, FlightStatus status);
}
