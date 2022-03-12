package com.henriquels25.airlines.plane.infra.stream;

import com.henriquels25.airlines.plane.TestData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(topics = {"plane-events-v1"},
        bootstrapServersProperty = "spring.cloud.stream.kafka.binder.brokers")
class StreamPlaneEventSenderTest {

    private static final String TOPIC_NAME = "plane-events-v1";

    @Autowired
    private StreamPlaneEventSender planeEventSender;

    @Autowired
    private EmbeddedKafkaBroker broker;

    private KafkaTestUtils kafkaTestUtils;

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @Test
    void shouldSendEventToTopic() throws JSONException {
        Consumer<String, String> consumer =
                kafkaTestUtils.createConsumer(TOPIC_NAME);

        planeEventSender.send(TestData.PLANE_EVENT);

        ConsumerRecord<String, String> record = kafkaTestUtils.getNextRecord(consumer, TOPIC_NAME);

        var jsonEvent = new JSONObject(record.value());
        assertThat(jsonEvent.get("planeId")).isEqualTo(TestData.PLANE_ID);
        assertThat(jsonEvent.get("currentAirport")).isEqualTo(TestData.POA_CODE);
    }

}