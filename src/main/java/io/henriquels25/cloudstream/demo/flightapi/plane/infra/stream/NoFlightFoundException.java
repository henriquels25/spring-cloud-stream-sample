package io.henriquels25.cloudstream.demo.flightapi.plane.infra.stream;

class NoFlightFoundException extends RuntimeException {
    public NoFlightFoundException(String message) {
        super(message);
    }
}
