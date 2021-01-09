package io.henriquels25.cloudstream.demo.flightapi.flight.infra.stream;

import io.henriquels25.cloudstream.demo.flightapi.messaging.CloudStreamTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import static io.henriquels25.cloudstream.demo.flightapi.TestData.FLIGHT_ID;
import static org.assertj.core.api.Assertions.assertThat;

@CloudStreamTest
@Import(StreamFlightNotifications.class)
class StreamFlightNotificationsTest {

    @Autowired
    private OutputDestination target;

    @Autowired
    private StreamFlightNotifications notifications;

    @Test
    void flightArrived() throws JSONException {
        notifications.flightArrived(FLIGHT_ID);

        Message<byte[]> flightEvent = target.receive(0L,
                "flight-arrived-v1");

        var jsonFlightEvent = new JSONObject(new String(flightEvent.getPayload()));
        assertThat(jsonFlightEvent.get("flightId")).isEqualTo(FLIGHT_ID);
    }
}