package com.henriquels25.flightapi.flight.infra.stream;

import com.henriquels25.flightapi.flight.FlightOperations;
import com.henriquels25.flightapi.messaging.utils.KafkaTestUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static com.henriquels25.flightapi.TestData.FLIGHT_ID;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FlightEventTestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(topics = {"flight-finished-v1"},
        bootstrapServersProperty = "spring.cloud.stream.kafka.binder.brokers")
@DirtiesContext
class StreamFlightNotificationsKafkaTest {

    @MockBean
    private FlightOperations flightOperations;

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private StreamFlightNotifications notifications;

    private KafkaTestUtils kafkaTestUtils;

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @Test
    void flightArrived() throws JSONException {
        Consumer<String, String> consumer = kafkaTestUtils.createConsumer("flight-finished-v1");

        notifications.flightFinished(FLIGHT_ID);

        ConsumerRecord<String, String> record = kafkaTestUtils.getNextRecord(consumer, "flight-finished-v1");

        var jsonFlightEvent = new JSONObject(record.value());
        assertThat(jsonFlightEvent.get("flightId")).isEqualTo(FLIGHT_ID);
    }
}