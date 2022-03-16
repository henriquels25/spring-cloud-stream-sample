package com.henriquels25.flightapi.flight.infra.stream;

import com.henriquels25.flightapi.messaging.utils.CloudStreamTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import static com.henriquels25.flightapi.TestData.FLIGHT_ID;
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
        notifications.flightFinished(FLIGHT_ID);

        Message<byte[]> flightEvent = target.receive(0L,
                "flight-finished-v1");

        var jsonFlightEvent = new JSONObject(new String(flightEvent.getPayload()));
        assertThat(jsonFlightEvent.get("flightId")).isEqualTo(FLIGHT_ID);
    }
}