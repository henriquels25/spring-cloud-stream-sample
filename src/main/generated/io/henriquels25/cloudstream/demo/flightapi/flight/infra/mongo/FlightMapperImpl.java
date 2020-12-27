package io.henriquels25.cloudstream.demo.flightapi.flight.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.airport.Airport;
import io.henriquels25.cloudstream.demo.flightapi.flight.Flight;
import io.henriquels25.cloudstream.demo.flightapi.plane.Plane;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-27T00:38:12-0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
class FlightMapperImpl implements FlightMapper {

    @Override
    public FlightDocument toDocument(Flight flight) {
        if ( flight == null ) {
            return null;
        }

        FlightDocument flightDocument = new FlightDocument();

        flightDocument.setPlaneId( flightPlaneId( flight ) );
        flightDocument.setOriginCode( flightOriginCode( flight ) );
        flightDocument.setDestinationCode( flightDestinationCode( flight ) );
        flightDocument.setId( flight.getId() );
        flightDocument.setStatus( flight.getStatus() );

        return flightDocument;
    }

    private String flightPlaneId(Flight flight) {
        if ( flight == null ) {
            return null;
        }
        Plane plane = flight.getPlane();
        if ( plane == null ) {
            return null;
        }
        String id = plane.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String flightOriginCode(Flight flight) {
        if ( flight == null ) {
            return null;
        }
        Airport origin = flight.getOrigin();
        if ( origin == null ) {
            return null;
        }
        String code = origin.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String flightDestinationCode(Flight flight) {
        if ( flight == null ) {
            return null;
        }
        Airport destination = flight.getDestination();
        if ( destination == null ) {
            return null;
        }
        String code = destination.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }
}
