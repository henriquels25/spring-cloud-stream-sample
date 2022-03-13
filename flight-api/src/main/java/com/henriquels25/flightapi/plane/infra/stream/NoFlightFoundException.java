package com.henriquels25.flightapi.plane.infra.stream;

class NoFlightFoundException extends RuntimeException {
    public NoFlightFoundException(String message) {
        super(message);
    }
}
