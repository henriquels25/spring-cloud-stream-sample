package com.henriquels25.airlines.plane;

import com.henriquels25.airlines.airport.Airport;
import com.henriquels25.airlines.plane.infra.stream.PlaneEvent;
import com.henriquels25.airlines.plane.infra.stream.PlaneEventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
class PlaneFacade implements PlaneOperations {

    private final PlaneRepository planeRepository;
    private final PlaneEventSender planeEventSender;

    @Override
    public String create(Plane plane) {
        return planeRepository.save(plane);
    }

    @Override
    public Optional<Plane> findById(String id) {
        return planeRepository.findById(id);
    }

    @Override
    public void arrivedIn(String planeId, Airport airport) {
        Plane plane = planeRepository.findById(planeId).orElseThrow(PlaneNotFoundException::new);
        planeRepository.save(plane.toBuilder().airport(airport).build());
        
        planeEventSender.send(PlaneEvent.builder().planeId(planeId).currentAirport(airport.getCode()).build());
    }
}
