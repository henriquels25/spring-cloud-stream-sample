package com.henriquels25.airlines.plane.infra.mongo;

import com.henriquels25.airlines.airport.Airport;
import com.henriquels25.airlines.airport.Airport.AirportBuilder;
import com.henriquels25.airlines.plane.Plane;
import com.henriquels25.airlines.plane.Plane.PlaneBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-06T20:01:34-0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
class PlaneMapperImpl implements PlaneMapper {

    @Override
    public PlaneDocument toPlaneDocument(Plane plane) {
        if ( plane == null ) {
            return null;
        }

        PlaneDocument planeDocument = new PlaneDocument();

        planeDocument.setId( plane.getId() );
        planeDocument.setCode( plane.getCode() );
        planeDocument.setType( plane.getType() );
        planeDocument.setAirport( airportToAirportDocument( plane.getAirport() ) );

        return planeDocument;
    }

    @Override
    public Plane toPlane(PlaneDocument plane) {
        if ( plane == null ) {
            return null;
        }

        PlaneBuilder plane1 = Plane.builder();

        plane1.id( plane.getId() );
        plane1.code( plane.getCode() );
        plane1.type( plane.getType() );
        plane1.airport( airportDocumentToAirport( plane.getAirport() ) );

        return plane1.build();
    }

    protected AirportDocument airportToAirportDocument(Airport airport) {
        if ( airport == null ) {
            return null;
        }

        AirportDocument airportDocument = new AirportDocument();

        airportDocument.setCode( airport.getCode() );

        return airportDocument;
    }

    protected Airport airportDocumentToAirport(AirportDocument airportDocument) {
        if ( airportDocument == null ) {
            return null;
        }

        AirportBuilder airport = Airport.builder();

        airport.code( airportDocument.getCode() );

        return airport.build();
    }
}
