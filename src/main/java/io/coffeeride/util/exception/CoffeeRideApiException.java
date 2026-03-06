package io.coffeeride.util.exception;

public final class CoffeeRideApiException extends Exception {

  public CoffeeRideApiException(String message, Throwable e) {
    super(message, e);
  }

}