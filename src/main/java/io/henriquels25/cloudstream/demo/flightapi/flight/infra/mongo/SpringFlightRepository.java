package io.henriquels25.cloudstream.demo.flightapi.flight.infra.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

interface SpringFlightRepository extends MongoRepository<FlightDocument, String> {
}
