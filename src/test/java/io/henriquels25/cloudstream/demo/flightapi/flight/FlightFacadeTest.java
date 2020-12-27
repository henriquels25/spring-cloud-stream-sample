package io.henriquels25.cloudstream.demo.flightapi.flight;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.henriquels25.cloudstream.demo.flightapi.TestData.FLIGHT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightFacadeTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightFacade flightFacade;

    private final static String CREATED_ID = "id";

    @Test
    void shouldSaveAFlight() {
        when(flightRepository.save(FLIGHT)).thenReturn(CREATED_ID);

        String id = flightFacade.create(FLIGHT);

        assertThat(id).isEqualTo(CREATED_ID);
        verify(flightRepository).save(FLIGHT);
    }

    @Test
    void shouldFindConfirmedFlightByPlaneIdWhenThereIsOneFlight() {
        String id = "id1";
        when(flightRepository.findConfirmedFlightsByPlaneId(id)).thenReturn(List.of(FLIGHT));

        Optional<Flight> optionalFlight = flightFacade.findConfirmedFlightByPlaneId(id);

        assertThat(optionalFlight).isPresent();
        Flight flight = optionalFlight.get();
        assertThat(flight).isEqualTo(FLIGHT);
    }

    @Test
    void shouldFindConfirmedFlightAndReturnEmptyWhenIsOneFlight() {
        String id = "id1";
        when(flightRepository.findConfirmedFlightsByPlaneId(id)).thenReturn(new ArrayList<>());

        Optional<Flight> optionalFlight = flightFacade.findConfirmedFlightByPlaneId(id);

        assertThat(optionalFlight).isNotPresent();
    }

    @Test
    void shouldFindConfirmedFlightsAndThrowIllegalStateWhenAreMoreThanOneFlight() {
        String id = "id1";
        when(flightRepository.findConfirmedFlightsByPlaneId(id)).thenReturn(List.of(FLIGHT, FLIGHT));

        assertThrows(IllegalStateException.class,
                () -> flightFacade.findConfirmedFlightByPlaneId(id));
    }

}
