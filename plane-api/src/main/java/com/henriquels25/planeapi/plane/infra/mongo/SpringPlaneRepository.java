package com.henriquels25.planeapi.plane.infra.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

interface SpringPlaneRepository extends MongoRepository<PlaneDocument, String> {
}
