package com.henriquels25.airlines.plane.infra.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("planes")
@Data
class PlaneDocument {

    @Id
    private String id;

    private String code;
    private String type;

    private AirportDocument airport;
}
