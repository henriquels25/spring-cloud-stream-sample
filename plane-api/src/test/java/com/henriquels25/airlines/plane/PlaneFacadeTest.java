package com.henriquels25.airlines.plane;

import com.henriquels25.airlines.plane.infra.stream.PlaneEvent;
import com.henriquels25.airlines.plane.infra.stream.PlaneEventSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.henriquels25.airlines.plane.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaneFacadeTest {

    @Mock
    private PlaneRepository planeRepository;

    @Mock
    private PlaneEventSender planeEventSender;

    @InjectMocks
    private PlaneFacade planeFacade;

    private final static String CREATED_ID = "id";

    @Test
    void shouldCreateAPlane() {
        when(planeRepository.save(PLANE)).thenReturn(CREATED_ID);

        String id = planeFacade.create(PLANE);

        assertThat(id).isEqualTo(CREATED_ID);
        verify(planeRepository).save(PLANE);
    }

    @Test
    void shouldFindByIdWhenPlaneExists() {
        when(planeRepository.findById(PLANE_ID)).thenReturn(Optional.of(PLANE));

        Optional<Plane> optPlane = planeFacade.findById(PLANE_ID);

        assertThat(optPlane).hasValue(PLANE);
        verify(planeRepository).findById(PLANE_ID);
    }

    @Test
    void shouldFindByIdWhenPlaneDoesNotExist() {
        when(planeRepository.findById(PLANE_ID)).thenReturn(Optional.empty());

        Optional<Plane> optPlane = planeFacade.findById(PLANE_ID);

        assertThat(optPlane).isEmpty();
        verify(planeRepository).findById(PLANE_ID);
    }

    @Test
    void shouldChangeAirportOfPlaneAndSendEvent() {
        when(planeRepository.findById(PLANE_ID)).thenReturn(Optional.of(PLANE));

        planeFacade.arrivedIn(PLANE_ID, POA_AIRPORT);

        verify(planeRepository).findById(PLANE_ID);
        verify(planeRepository).save(PLANE.toBuilder().airport(POA_AIRPORT).build());

        verify(planeEventSender).send(PlaneEvent.builder()
                .planeId(PLANE_ID)
                .currentAirport(POA_CODE)
                .build());

    }

    @Test
    void shouldThrowExceptionWhenPlaneDoesNotExist() {
        when(planeRepository.findById(PLANE_ID)).thenReturn(Optional.empty());

        assertThrows(PlaneNotFoundException.class, () -> planeFacade.arrivedIn(PLANE_ID, POA_AIRPORT));

        verify(planeRepository).findById(PLANE_ID);
        verifyNoMoreInteractions(planeRepository);
        verifyNoInteractions(planeEventSender);
    }
}
