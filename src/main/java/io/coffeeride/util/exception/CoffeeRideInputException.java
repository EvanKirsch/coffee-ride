package io.coffeeride.util.exception;

public final class CoffeeRideInputException extends Exception {

  public CoffeeRideInputException(String milesMustBeEntered) {
    super(milesMustBeEntered);
  }

}