package com.henriquels25.airlines.plane.infra.stream;

class NoFlightFoundException extends RuntimeException {
    public NoFlightFoundException(String message) {
        super(message);
    }
}
