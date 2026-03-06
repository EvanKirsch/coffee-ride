package io.coffeeride.controller;

import io.coffeeride.util.exception.CoffeeRideInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CoffeeRideExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(CoffeeRideExceptionHandler.class);

  @ExceptionHandler(CoffeeRideInputException.class)
  public ResponseEntity<String> handleError(CoffeeRideInputException e) {
    log.warn("User Input Exception: {}", e.getMessage());
    return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleError(Exception e) {
    log.warn("Handling Response Error : {}", e.getMessage());
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}