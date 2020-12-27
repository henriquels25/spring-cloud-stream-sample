package io.henriquels25.cloudstream.demo.flightapi.plane.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.plane.Plane;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

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
        assertThat(mongoTemplate.findAll(PlaneDocument.class)).hasSize(1);
    }
}