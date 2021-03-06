package io.henriquels25.cloudstream.demo.flightapi.acceptance.flight;

import io.henriquels25.cloudstream.demo.flightapi.acceptance.AcceptanceTest;
import io.henriquels25.cloudstream.demo.flightapi.flight.Flight;
import io.henriquels25.cloudstream.demo.flightapi.flight.FlightRepository;
import io.henriquels25.cloudstream.demo.flightapi.messaging.utils.KafkaTestUtils;
import io.henriquels25.cloudstream.demo.flightapi.plane.PlaneRepository;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.Callable;

import static io.henriquels25.cloudstream.demo.flightapi.TestData.*;
import static io.henriquels25.cloudstream.demo.flightapi.flight.FlightStatus.ARRIVED;
import static io.henriquels25.cloudstream.demo.flightapi.flight.FlightStatus.CONFIRMED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(topics = {"plane-events-v1",
        "flight-events-v1", "plane-events-dlq-v1", "flight-events-dlq-v1", "flight-arrived-v1"},
        bootstrapServersProperty = "spring.cloud.stream.kafka.binder.brokers")
class FlightArrivedAcceptanceTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private EmbeddedKafkaBroker broker;

    private KafkaTestUtils kafkaTestUtils;

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @AcceptanceTest
    void shouldCreateAFlightAndChangeTheStatusToArrived() throws JSONException {
        Consumer<String, String> consumer = kafkaTestUtils.createConsumer("flight-arrived-v1");
        String planeId = planeRepository.save(PLANE);

        Flight flight = Flight.builder().plane(PLANE.builder().id(planeId).build()).origin(POA)
                .destination(CNH)
                .status(CONFIRMED).build();
        String flightId = flightRepository.save(flight);

        String flightEvent = new JSONObject().put("flightId", flightId)
                .put("currentAirport", CNH_CODE).toString();

        kafkaTestUtils.sendMessage("flight-events-v1", flightEvent);

        await().until(flightHasArrivedStatus(flightId));

        ConsumerRecord<String, String> record = kafkaTestUtils
                .getNextRecord(consumer, "flight-arrived-v1");

        var jsonArrivedEvent = new JSONObject(record.value());
        assertThat(jsonArrivedEvent.get("flightId")).isEqualTo(flightId);
    }

    private Callable<Boolean> flightHasArrivedStatus(String flightId) {
        return () -> flightRepository.findById(flightId).get().getStatus() == ARRIVED;
    }

}
