package io.henriquels25.cloudstream.demo.flightapi.flight;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class FlightFacade implements FlightOperations {

    private final FlightRepository flightRepository;

    @Override
    public String create(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Optional<Flight> findConfirmedFlightByPlaneId(String planeId) {
        List<Flight> flights = flightRepository.findConfirmedFlightsByPlaneId(planeId);
        if (flights.isEmpty()) {
            return Optional.empty();
        }
        if (flights.size() > 1) {
            throw new IllegalStateException("There should only be one confirmed flight for a plane");
        }
        return Optional.of(flights.get(0));
    }
}
