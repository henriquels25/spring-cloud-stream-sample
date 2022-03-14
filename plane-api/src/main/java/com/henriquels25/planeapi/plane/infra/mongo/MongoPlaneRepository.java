package com.henriquels25.planeapi.plane.infra.mongo;

import com.henriquels25.planeapi.plane.Plane;
import com.henriquels25.planeapi.plane.PlaneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @Override
    public Optional<Plane> findById(String id) {
        return springPlaneRepository.findById(id)
                .map(planeMapper::toPlane);
    }
}
