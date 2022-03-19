package com.henriquels25.flightapi.flight.infra.stream.consumer;

import com.henriquels25.flightapi.airport.Airport;
import com.henriquels25.flightapi.flight.FlightOperations;
import com.henriquels25.flightapi.messaging.utils.EmbeddedKafkaWithTopics;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafkaWithTopics
class FlightArrivedConsumerTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    @MockBean
    private FlightOperations flightOperations;

    private KafkaTestUtils kafkaTestUtils;

    private static final String FLIGHT_ID = UUID.randomUUID().toString();
    private static final String AIRPORT = "ABB";

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @Test
    void shouldCallFlightOperationsWhenMessageIsReceived() throws JSONException {
        String flightEvent = new JSONObject().put("flightId", FLIGHT_ID)
                .put("currentAirport", AIRPORT).toString();

        kafkaTestUtils.sendMessage("flight-arrived-v1", flightEvent);

        await().untilAsserted(() -> verify(flightOperations).
                flightArrivedIn(FLIGHT_ID, new Airport(AIRPORT)));
    }


    @Test
    void shouldSendToDeadLetterWhenAnExceptionHappens() throws JSONException, InterruptedException {
        doThrow(RuntimeException.class)
                .when(flightOperations).flightArrivedIn(FLIGHT_ID, new Airport(AIRPORT));

        Consumer<String, String> consumer = kafkaTestUtils.createConsumer("flight-arrived-dlq-v1");

        String flightEvent = new JSONObject().put("flightId", FLIGHT_ID)
                .put("currentAirport", AIRPORT).toString();

        kafkaTestUtils.sendMessage("flight-arrived-v1", flightEvent);

        ConsumerRecord<String, String> record = kafkaTestUtils.getLastRecord(consumer, "flight-arrived-dlq-v1");

        var jsonFlightEvent = new JSONObject(record.value());
        assertThat(jsonFlightEvent.get("currentAirport")).isEqualTo(AIRPORT);
        assertThat(jsonFlightEvent.get("flightId")).isEqualTo(FLIGHT_ID);
        String exceptionMessage = new String(record.headers().lastHeader("x-exception-message").value());
        assertThat(exceptionMessage).contains("RuntimeException");

        verify(flightOperations, times(3)).flightArrivedIn(FLIGHT_ID,
                new Airport(AIRPORT));
    }
}
