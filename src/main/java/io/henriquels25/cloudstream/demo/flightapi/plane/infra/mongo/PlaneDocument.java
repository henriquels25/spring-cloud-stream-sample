package io.henriquels25.cloudstream.demo.flightapi.plane.infra.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
class PlaneDocument {

    @Id
    private String id;
    
    private String code;
    private String type;
}
