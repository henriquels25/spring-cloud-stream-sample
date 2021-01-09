package io.henriquels25.cloudstream.demo.flightapi.flight.infra.stream;

import io.henriquels25.cloudstream.demo.flightapi.airport.Airport;
import io.henriquels25.cloudstream.demo.flightapi.flight.FlightOperations;
import io.henriquels25.cloudstream.demo.flightapi.messaging.utils.KafkaTestUtils;
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

import static io.henriquels25.cloudstream.demo.flightapi.TestData.CNH_CODE;
import static io.henriquels25.cloudstream.demo.flightapi.TestData.FLIGHT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@SpringBootTest
@EmbeddedKafka(topics = {"plane-events-v1",
        "flight-events-v1", "plane-events-dlq-v1", "flight-events-dlq-v1"},
        bootstrapServersProperty = "spring.cloud.stream.kafka.binder.brokers")
class FlightEventKafkaTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    @MockBean
    private FlightOperations flightOperations;

    private KafkaTestUtils kafkaTestUtils;

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @Test
    void shouldCallFlightOperationsWhenMessageIsReceived() throws JSONException, InterruptedException {
        String flightEvent = new JSONObject().put("flightId", FLIGHT_ID)
                .put("currentAirport", CNH_CODE).toString();

        kafkaTestUtils.sendMessage("flight-events-v1", flightEvent);

        await().untilAsserted(() -> verify(flightOperations).
                flightArrivedIn(FLIGHT_ID, new Airport(CNH_CODE)));
    }


    @Test
    void shouldSendToDeadLetterWhenAnExceptionHappens() throws JSONException, InterruptedException {
        doThrow(RuntimeException.class)
                .when(flightOperations).flightArrivedIn(FLIGHT_ID, new Airport(CNH_CODE));

        Consumer<String, String> consumer = kafkaTestUtils.createConsumer("flight-events-dlq-v1");

        String flightEvent = new JSONObject().put("flightId", FLIGHT_ID)
                .put("currentAirport", CNH_CODE).toString();

        kafkaTestUtils.sendMessage("flight-events-v1", flightEvent);

        ConsumerRecord<String, String> record = kafkaTestUtils.getNextRecord(consumer, "flight-events-dlq-v1");

        var jsonFlightEvent = new JSONObject(record.value());
        assertThat(jsonFlightEvent.get("currentAirport")).isEqualTo(CNH_CODE);
        assertThat(jsonFlightEvent.get("flightId")).isEqualTo(FLIGHT_ID);
        String exceptionMessage = new String(record.headers().lastHeader("x-exception-message").value());
        assertThat(exceptionMessage).contains("RuntimeException");

        verify(flightOperations, times(3)).flightArrivedIn(FLIGHT_ID,
                new Airport(CNH_CODE));
    }
}
