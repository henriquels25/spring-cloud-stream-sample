package io.henriquels25.cloudstream.demo.flightapi.flight.infra.stream;

import io.henriquels25.cloudstream.demo.flightapi.airport.Airport;
import io.henriquels25.cloudstream.demo.flightapi.flight.FlightOperations;
import io.henriquels25.cloudstream.demo.flightapi.messaging.CloudStreamTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.support.GenericMessage;

import static io.henriquels25.cloudstream.demo.flightapi.TestData.CNH_CODE;
import static io.henriquels25.cloudstream.demo.flightapi.TestData.FLIGHT_ID;
import static org.mockito.Mockito.verify;

@CloudStreamTest(FlightEventConsumer.class)
class FlightEventConsumerTest {

    @Autowired
    private InputDestination source;
    @Autowired
    private OutputDestination target;

    @MockBean
    private FlightOperations flightOperations;

    @Test
    void shouldCallFlightOperationsWhenMessageIsReceived() throws JSONException {
        String planeEvent = new JSONObject().put("flightId", FLIGHT_ID)
                .put("currentAirport", CNH_CODE).toString();

        source.send(new GenericMessage<>(planeEvent.getBytes()),
                "flight-events-v1");

        verify(flightOperations).flightArrivedIn(FLIGHT_ID, new Airport(CNH_CODE));
    }

}
