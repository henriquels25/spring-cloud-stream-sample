package com.henriquels25.flightapi.flight.infra.mongo;

import com.henriquels25.flightapi.flight.Flight;
import com.henriquels25.flightapi.flight.FlightRepository;
import com.henriquels25.flightapi.flight.FlightStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
class FlightMongoRepository implements FlightRepository {

    private final FlightMapper flightMapper;
    private final SpringFlightRepository springFlightRepository;

    @Override
    public String save(Flight flight) {
        FlightDocument saved = springFlightRepository.save(flightMapper.toDocument(flight));
        return saved.getId();
    }

    @Override
    public List<Flight> findConfirmedFlightsByPlaneId(String planeId) {
        List<FlightDocument> documents = springFlightRepository.
                findByPlaneIdAndStatus(planeId, FlightStatus.CONFIRMED);
        return documents.stream().map(flightMapper::fromDocument).toList();
    }

    @Override
    public Optional<Flight> findById(String flightId) {
        return springFlightRepository.findById(flightId).map(flightMapper::fromDocument);
    }
}
