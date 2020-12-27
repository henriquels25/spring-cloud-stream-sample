package io.henriquels25.cloudstream.demo.flightapi.plane.infra.mongo;

import io.henriquels25.cloudstream.demo.flightapi.plane.Plane;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-27T00:38:12-0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
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

        return planeDocument;
    }
}
