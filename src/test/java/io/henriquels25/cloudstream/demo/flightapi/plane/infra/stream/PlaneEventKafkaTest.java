package io.henriquels25.cloudstream.demo.flightapi.plane.infra.stream;

import io.henriquels25.cloudstream.demo.flightapi.flight.FlightOperations;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.Optional;

import static io.henriquels25.cloudstream.demo.flightapi.TestData.*;
import static io.henriquels25.cloudstream.demo.flightapi.messaging.utils.KafkaConsumerUtils.createConsumer;
import static io.henriquels25.cloudstream.demo.flightapi.messaging.utils.KafkaConsumerUtils.getNextRecord;
import static io.henriquels25.cloudstream.demo.flightapi.messaging.utils.KafkaProducerUtils.createProducer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@EmbeddedKafka(topics = {"plane-events-v1",
        "flight-events-v1", "plane-events-dlq-v1", "flight-events-dlq-v1"},
        bootstrapServersProperty = "spring.cloud.stream.kafka.binder.brokers")
class PlaneEventKafkaTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    @MockBean
    private FlightOperations flightOperations;

    @Test
    void shouldTransformPlaneEventToFlightEvent() throws JSONException {
        when(flightOperations.findConfirmedFlightByPlaneId(PLANE_ID)).
                thenReturn(Optional.of(FLIGHT_WITH_ID));

        Consumer<String, String> consumer = createConsumer(broker, "flight-events-v1");

        String planeEvent = new JSONObject().put("planeId", PLANE_ID)
                .put("currentAirport", CNH_CODE).toString();

        sendMessage("plane-events-v1", planeEvent);

        ConsumerRecord<String, String> record = getNextRecord(consumer, "flight-events-v1");

        var jsonFlightEvent = new JSONObject(record.value());
        assertThat(jsonFlightEvent.get("currentAirport")).isEqualTo(CNH_CODE);
        assertThat(jsonFlightEvent.get("flightId")).isEqualTo(FLIGHT_ID);
    }

    @Test
    void shouldSendToDeadLetterWhenThereIsNoFlight() throws JSONException {
        when(flightOperations.findConfirmedFlightByPlaneId(PLANE_ID)).
                thenReturn(Optional.empty());

        Consumer<String, String> consumer = createConsumer(broker, "plane-events-dlq-v1");

        String planeEvent = new JSONObject().put("planeId", PLANE_ID)
                .put("currentAirport", CNH_CODE).toString();

        sendMessage("plane-events-v1", planeEvent);

        ConsumerRecord<String, String> record = getNextRecord(consumer, "plane-events-dlq-v1");

        var jsonFlightEvent = new JSONObject(record.value());
        assertThat(jsonFlightEvent.get("currentAirport")).isEqualTo(CNH_CODE);
        assertThat(jsonFlightEvent.get("planeId")).isEqualTo(PLANE_ID);
        String exceptionMessage = new String(record.headers().lastHeader("x-exception-message").value());
        assertThat(exceptionMessage).contains("NoFlightFoundException");
    }


    private void sendMessage(String topic, String json) {
        Producer<String, String> producer = createProducer(broker);

        producer.send(new ProducerRecord<>(topic, json));
    }

}
