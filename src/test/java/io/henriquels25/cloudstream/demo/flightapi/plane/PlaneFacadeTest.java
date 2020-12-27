package io.henriquels25.cloudstream.demo.flightapi.plane;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.henriquels25.cloudstream.demo.flightapi.TestData.PLANE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaneFacadeTest {

    @Mock
    private PlaneRepository planeRepository;

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

}
