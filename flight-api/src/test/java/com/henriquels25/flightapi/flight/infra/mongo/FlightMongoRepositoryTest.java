package com.henriquels25.flightapi.flight.infra.mongo;

import com.henriquels25.flightapi.TestData;
import com.henriquels25.flightapi.flight.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static com.henriquels25.flightapi.flight.FlightStatus.ARRIVED;
import static com.henriquels25.flightapi.flight.FlightStatus.CONFIRMED;
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
        Flight flight = Flight.builder().plane(TestData.PLANE_WITH_ID).origin(TestData.POA)
                .destination(TestData.CNH)
                .status(CONFIRMED).build();
        String id = flightMongoRepository.save(flight);

        assertThat(id).isNotEmpty();
        FlightDocument document = mongoTemplate.findById(id, FlightDocument.class);
        assertThat(document.getPlaneId()).isEqualTo(TestData.PLANE_ID);
        assertThat(document.getOriginCode()).isEqualTo(TestData.POA_CODE);
        assertThat(document.getDestinationCode()).isEqualTo(TestData.CNH_CODE);
        assertThat(document.getStatus()).isEqualTo(CONFIRMED);
    }

    @Test
    void shouldUpdateAFlightStatusWhenCallingSave() {
        Flight flight = Flight.builder().plane(TestData.PLANE_WITH_ID).origin(TestData.POA)
                .destination(TestData.CNH)
                .status(CONFIRMED).build();
        String id = flightMongoRepository.save(flight);
        Flight updatedFlight = flight.toBuilder().id(id).status(ARRIVED).build();

        flightMongoRepository.save(updatedFlight);

        Flight flightDb = flightMongoRepository.findById(id).get();

        assertThat(flightDb.getStatus()).isEqualTo(ARRIVED);
    }

    @Test
    void shouldFindAConfirmedFlightByPlaneId() {
        flightMongoRepository.save(Flight.builder().plane(TestData.PLANE_WITH_ID).origin(TestData.CNH)
                .destination(TestData.POA)
                .status(CONFIRMED).build());

        flightMongoRepository.save(Flight.builder().plane(TestData.PLANE_WITH_ID).origin(TestData.POA)
                .destination(TestData.CNH)
                .status(CONFIRMED).build());

        flightMongoRepository.save(Flight.builder().plane(TestData.PLANE_WITH_ID_2).origin(TestData.POA)
                .destination(TestData.CNH)
                .status(CONFIRMED).build());

        List<Flight> flights = flightMongoRepository.findConfirmedFlightsByPlaneId(TestData.PLANE_ID);

        assertThat(flights).hasSize(2);
    }

    @Test
    void shouldFindAFlightById() {
        flightMongoRepository.save(TestData.FLIGHT_WITH_ID);
        flightMongoRepository.save(TestData.FLIGHT_WITH_ID_2);

        Optional<Flight> flightOptional = flightMongoRepository.findById(TestData.FLIGHT_ID);

        assertThat(flightOptional).isPresent();
        Flight flight = flightOptional.get();
        assertThat(flight.getId()).isEqualTo(TestData.FLIGHT_ID);
        assertThat(flight.getPlane().getId()).isEqualTo(TestData.PLANE_ID);
    }

    @Test
    void shouldNotFindAFlightByIdWhenItDoesNotExist() {
        flightMongoRepository.save(TestData.FLIGHT_WITH_ID);
        flightMongoRepository.save(TestData.FLIGHT_WITH_ID_2);

        Optional<Flight> flightOptional = flightMongoRepository.findById("wrongId");

        assertThat(flightOptional).isEmpty();
    }

}
