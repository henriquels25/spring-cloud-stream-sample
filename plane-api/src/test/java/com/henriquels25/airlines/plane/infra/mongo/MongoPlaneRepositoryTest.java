package com.henriquels25.airlines.plane.infra.mongo;

import com.henriquels25.airlines.plane.Plane;
import com.henriquels25.airlines.plane.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import({MongoPlaneRepository.class, PlaneMapperImpl.class})
class MongoPlaneRepositoryTest {

    @Autowired
    private MongoPlaneRepository mongoPlaneRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String CODE = "PR-MYK";
    private final static String TYPE = "Airbus A320";

    @Test
    void shouldCreateAPlane() {
        Plane plane = Plane.builder().code(CODE).type(TYPE).build();

        String id = mongoPlaneRepository.save(plane);

        assertThat(id).isNotEmpty();
        assertThat(mongoPlaneRepository.findById(id)).isPresent();
    }


    @Test
    void shouldFindAPlaneById() {
        Plane plane = Plane.builder().code(CODE).type(TYPE).build();
        String id = mongoPlaneRepository.save(plane);

        Optional<Plane> planeOptional = mongoPlaneRepository.findById(id);

        assertThat(planeOptional).hasValue(plane.toBuilder().id(id).build());
    }

    @Test
    void shouldChangeAirportOfPlane() {
        Plane plane = Plane.builder().code(CODE).type(TYPE).build();
        String id = mongoPlaneRepository.save(plane);

        plane = mongoPlaneRepository.findById(id).get();
        plane = plane.toBuilder().airport(TestData.POA_AIRPORT).build();

        mongoPlaneRepository.save(plane);
        plane = mongoPlaneRepository.findById(id).get();

        assertThat(plane.getAirport()).isEqualTo(TestData.POA_AIRPORT);
    }
}