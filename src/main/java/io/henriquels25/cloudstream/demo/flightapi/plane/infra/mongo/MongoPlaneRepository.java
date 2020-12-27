package io.henriquels25.cloudstream.demo.flightapi.plane.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.plane.Plane;
import io.henriquels25.cloudstream.demo.flightapi.plane.PlaneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class MongoPlaneRepository implements PlaneRepository {

    private final PlaneMapper planeMapper;
    private final SpringPlaneRepository springPlaneRepository;

    @Override
    public String save(Plane plane) {
        PlaneDocument savedPlane =
                springPlaneRepository.save(planeMapper.toPlaneDocument(plane));
        return savedPlane.getId();
    }
}
