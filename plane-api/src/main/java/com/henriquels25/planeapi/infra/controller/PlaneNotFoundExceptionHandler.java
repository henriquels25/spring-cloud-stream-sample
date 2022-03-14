package com.henriquels25.planeapi.infra.controller;

import com.henriquels25.planeapi.plane.PlaneNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlaneNotFoundExceptionHandler {

    private static final String MESSAGE = "Plane not found";

    @ExceptionHandler(PlaneNotFoundException.class)
    public ResponseEntity<Error> handle() {
        return ResponseEntity.badRequest().body(new Error(MESSAGE));
    }

}
