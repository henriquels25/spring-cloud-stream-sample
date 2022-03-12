package com.henriquels25.airlines.flight.infra.mongo;

import com.henriquels25.airlines.flight.Flight;
import com.henriquels25.airlines.flight.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.henriquels25.airlines.flight.FlightStatus.CONFIRMED;
import static java.util.stream.Collectors.toList;

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
                findByPlaneIdAndStatus(planeId, CONFIRMED);
        return documents.stream().map(flightMapper::fromDocument).toList();
    }

    @Override
    public Optional<Flight> findById(String flightId) {
        return springFlightRepository.findById(flightId).map(flightMapper::fromDocument);
    }
}
