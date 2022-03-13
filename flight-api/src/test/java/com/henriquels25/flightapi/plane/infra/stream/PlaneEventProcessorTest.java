package com.henriquels25.flightapi.plane.infra.stream;

import com.henriquels25.flightapi.flight.FlightOperations;
import com.henriquels25.flightapi.messaging.utils.CloudStreamTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.Optional;

import static com.henriquels25.flightapi.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@CloudStreamTest
@Import(PlaneEventProcessor.class)
class PlaneEventProcessorTest {

    @Autowired
    private InputDestination source;
    @Autowired
    private OutputDestination target;

    @MockBean
    private FlightOperations flightOperations;

    @Test
    void shouldTransformPlaneEventToFlightEvent() throws JSONException {
        when(flightOperations.findConfirmedFlightByPlaneId(PLANE_ID)).
                thenReturn(Optional.of(FLIGHT_WITH_ID));

        String planeEvent = new JSONObject().put("planeId", PLANE_ID)
                .put("currentAirport", CNH_CODE).toString();

        source.send(new GenericMessage<>(planeEvent.getBytes()),
                "plane-events-v1");

        Message<byte[]> flightEvent = target.receive(0L,
                "flight-events-v1");

        var jsonFlightEvent = new JSONObject(new String(flightEvent.getPayload()));
        assertThat(jsonFlightEvent.get("currentAirport")).isEqualTo(CNH_CODE);
        assertThat(jsonFlightEvent.get("flightId")).isEqualTo(FLIGHT_ID);

        verify(flightOperations).findConfirmedFlightByPlaneId(PLANE_ID);
    }

    @Test
    void shouldNotTransformPlaneEventToFlightEventWhenThereIsNoFlight() throws JSONException {
        when(flightOperations.findConfirmedFlightByPlaneId(PLANE_ID)).
                thenReturn(Optional.empty());

        String planeEvent = new JSONObject().put("planeId", PLANE_ID)
                .put("currentAirport", CNH_CODE).toString();

        source.send(new GenericMessage<>(planeEvent.getBytes()),
                "plane-events-v1");

        Message<byte[]> flightEvent = target.receive(0L,
                "flight-events-v1");

        assertThat(flightEvent).isNull();

        verify(flightOperations).findConfirmedFlightByPlaneId(PLANE_ID);
    }

    @Test
    void shouldTry3TimesToProcessPlaneEventWhenUnexpectedErrorHappens() throws JSONException {
        when(flightOperations.findConfirmedFlightByPlaneId(PLANE_ID)).
                thenThrow(RuntimeException.class);

        String planeEvent = new JSONObject().put("planeId", PLANE_ID)
                .put("currentAirport", CNH_CODE).toString();

        source.send(new GenericMessage<>(planeEvent.getBytes()),
                "plane-events-v1");

        Message<byte[]> flightEvent = target.receive(0L,
                "flight-events-v1");

        assertThat(flightEvent).isNull();

        verify(flightOperations, Mockito.times(3))
                .findConfirmedFlightByPlaneId(PLANE_ID);
    }

}
