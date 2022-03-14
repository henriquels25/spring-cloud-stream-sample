package com.henriquels25.planeapi.plane;

import com.henriquels25.planeapi.plane.infra.stream.PlaneEvent;
import com.henriquels25.planeapi.plane.infra.stream.PlaneEventSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        when(planeRepository.save(TestData.PLANE)).thenReturn(CREATED_ID);

        String id = planeFacade.create(TestData.PLANE);

        assertThat(id).isEqualTo(CREATED_ID);
        verify(planeRepository).save(TestData.PLANE);
    }

    @Test
    void shouldFindByIdWhenPlaneExists() {
        when(planeRepository.findById(TestData.PLANE_ID)).thenReturn(Optional.of(TestData.PLANE));

        Optional<Plane> optPlane = planeFacade.findById(TestData.PLANE_ID);

        assertThat(optPlane).hasValue(TestData.PLANE);
        verify(planeRepository).findById(TestData.PLANE_ID);
    }

    @Test
    void shouldFindByIdWhenPlaneDoesNotExist() {
        when(planeRepository.findById(TestData.PLANE_ID)).thenReturn(Optional.empty());

        Optional<Plane> optPlane = planeFacade.findById(TestData.PLANE_ID);

        assertThat(optPlane).isEmpty();
        verify(planeRepository).findById(TestData.PLANE_ID);
    }

    @Test
    void shouldChangeAirportOfPlaneAndSendEvent() {
        when(planeRepository.findById(TestData.PLANE_ID)).thenReturn(Optional.of(TestData.PLANE));

        planeFacade.arrivedIn(TestData.PLANE_ID, TestData.POA_AIRPORT);

        verify(planeRepository).findById(TestData.PLANE_ID);
        verify(planeRepository).save(TestData.PLANE.toBuilder().airport(TestData.POA_AIRPORT).build());

        verify(planeEventSender).send(PlaneEvent.builder()
                .planeId(TestData.PLANE_ID)
                .currentAirport(TestData.POA_CODE)
                .build());

    }

    @Test
    void shouldThrowExceptionWhenPlaneDoesNotExist() {
        when(planeRepository.findById(TestData.PLANE_ID)).thenReturn(Optional.empty());

        assertThrows(PlaneNotFoundException.class, () -> planeFacade.arrivedIn(TestData.PLANE_ID, TestData.POA_AIRPORT));

        verify(planeRepository).findById(TestData.PLANE_ID);
        verifyNoMoreInteractions(planeRepository);
        verifyNoInteractions(planeEventSender);
    }
}
