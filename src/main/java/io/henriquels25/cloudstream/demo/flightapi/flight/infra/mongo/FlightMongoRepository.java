package io.henriquels25.cloudstream.demo.flightapi.flight.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.flight.Flight;
import io.henriquels25.cloudstream.demo.flightapi.flight.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
