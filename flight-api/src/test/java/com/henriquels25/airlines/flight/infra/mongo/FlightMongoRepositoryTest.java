package com.henriquels25.airlines.flight.infra.mongo;

import com.henriquels25.airlines.flight.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static com.henriquels25.airlines.TestData.*;
import static com.henriquels25.airlines.flight.FlightStatus.ARRIVED;
import static com.henriquels25.airlines.flight.FlightStatus.CONFIRMED;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import({FlightMongoRepository.class, FlightMapperImpl.class})
class FlightMongoRepositoryTest {

    @Autowired
    private FlightMongoRepository flightMongoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void shouldSaveAFlight() {
        Flight flight = Flight.builder().plane(PLANE_WITH_ID).origin(POA)
                .destination(CNH)
                .status(CONFIRMED).build();
        String id = flightMongoRepository.save(flight);

        assertThat(id).isNotEmpty();
        FlightDocument document = mongoTemplate.findById(id, FlightDocument.class);
        assertThat(document.getPlaneId()).isEqualTo(PLANE_ID);
        assertThat(document.getOriginCode()).isEqualTo(POA_CODE);
        assertThat(document.getDestinationCode()).isEqualTo(CNH_CODE);
        assertThat(document.getStatus()).isEqualTo(CONFIRMED);
    }

    @Test
    void shouldUpdateAFlightStatusWhenCallingSave() {
        Flight flight = Flight.builder().plane(PLANE_WITH_ID).origin(POA)
                .destination(CNH)
                .status(CONFIRMED).build();
        String id = flightMongoRepository.save(flight);
        Flight updatedFlight = flight.toBuilder().id(id).status(ARRIVED).build();

        flightMongoRepository.save(updatedFlight);

        Flight flightDb = flightMongoRepository.findById(id).get();

        assertThat(flightDb.getStatus()).isEqualTo(ARRIVED);
    }

    @Test
    void shouldFindAConfirmedFlightByPlaneId() {
        flightMongoRepository.save(Flight.builder().plane(PLANE_WITH_ID).origin(CNH)
                .destination(POA)
                .status(CONFIRMED).build());

        flightMongoRepository.save(Flight.builder().plane(PLANE_WITH_ID).origin(POA)
                .destination(CNH)
                .status(CONFIRMED).build());

        flightMongoRepository.save(Flight.builder().plane(PLANE_WITH_ID_2).origin(POA)
                .destination(CNH)
                .status(CONFIRMED).build());

        List<Flight> flights = flightMongoRepository.findConfirmedFlightsByPlaneId(PLANE_ID);

        assertThat(flights).hasSize(2);
    }

    @Test
    void shouldFindAFlightById() {
        flightMongoRepository.save(FLIGHT_WITH_ID);
        flightMongoRepository.save(FLIGHT_WITH_ID_2);

        Optional<Flight> flightOptional = flightMongoRepository.findById(FLIGHT_ID);

        assertThat(flightOptional).isPresent();
        Flight flight = flightOptional.get();
        assertThat(flight.getId()).isEqualTo(FLIGHT_ID);
        assertThat(flight.getPlane().getId()).isEqualTo(PLANE_ID);
    }

    @Test
    void shouldNotFindAFlightByIdWhenItDoesNotExist() {
        flightMongoRepository.save(FLIGHT_WITH_ID);
        flightMongoRepository.save(FLIGHT_WITH_ID_2);

        Optional<Flight> flightOptional = flightMongoRepository.findById("wrongId");

        assertThat(flightOptional).isEmpty();
    }

}
