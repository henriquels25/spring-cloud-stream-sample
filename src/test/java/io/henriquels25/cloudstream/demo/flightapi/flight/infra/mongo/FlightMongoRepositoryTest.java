package io.henriquels25.cloudstream.demo.flightapi.flight.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.flight.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import static io.henriquels25.cloudstream.demo.flightapi.TestData.*;
import static io.henriquels25.cloudstream.demo.flightapi.flight.FlightStatus.CONFIRMED;
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

}
