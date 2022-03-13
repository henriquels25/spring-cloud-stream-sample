package com.henriquels25.flightapi.flight.infra.mongo;

import com.henriquels25.flightapi.flight.FlightStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
class FlightDocument {
    private String id;
    private String planeId;
    private String originCode;
    private String destinationCode;
    private FlightStatus status;
}
