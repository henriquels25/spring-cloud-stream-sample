package com.henriquels25.flightapi.e2e;

import com.henriquels25.flightapi.messaging.utils.EmbeddedKafkaWithTopics;
import com.henriquels25.flightapi.messaging.utils.KafkaTestUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.henriquels25.flightapi.TestData.CNH_CODE;
import static com.henriquels25.flightapi.TestData.POA_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EmbeddedKafkaWithTopics
@AutoConfigureMockMvc
class FlightApiAcceptanceTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private MockMvc mockMvc;

    private KafkaTestUtils kafkaTestUtils;

    private static final String PLANE_ID = UUID.randomUUID().toString();

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @Test
    void shouldReceiveAPlaneEventAndSendAFlightFinishedEvent() throws Exception {
        Consumer<String, String> consumer = kafkaTestUtils.createConsumer("flight-finished-v1");

        var jsonFlightEvent = new JSONObject();
        jsonFlightEvent.put("planeId", PLANE_ID);
        jsonFlightEvent.put("origin", POA_CODE);
        jsonFlightEvent.put("destination", CNH_CODE);

        String locationHeader = mockMvc.perform(post("/flights").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFlightEvent.toString()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("location");

        String flightId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
        
        var planeEvent = new JSONObject();
        planeEvent.put("planeId", PLANE_ID);
        planeEvent.put("currentAirport", CNH_CODE);

        kafkaTestUtils.sendMessage("plane-arrived-v1", planeEvent.toString());

        ConsumerRecord<String, String> record = kafkaTestUtils
                .getLastRecord(consumer, "flight-finished-v1");

        JSONObject jsonFlightFinished = new JSONObject(record.value());

        assertThat(jsonFlightFinished.get("flightId")).isEqualTo(flightId);
    }

}
