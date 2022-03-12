package com.henriquels25.airlines.flight.infra.stream;

import com.henriquels25.airlines.airport.Airport;
import com.henriquels25.airlines.flight.FlightOperations;
import com.henriquels25.airlines.messaging.utils.CloudStreamTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import static com.henriquels25.airlines.TestData.CNH_CODE;
import static com.henriquels25.airlines.TestData.FLIGHT_ID;
import static org.mockito.Mockito.verify;

@CloudStreamTest
@Import(FlightEventConsumer.class)
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
