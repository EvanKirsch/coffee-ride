package org.kirsch.model.gcs;


public final class Coordinate {

  // value stored in degrees
  private final double degrees;

  private Coordinate(double degrees) {
    this.degrees = degrees;
  }

  public double toDegrees() {
    return degrees;
  }

  public double toRadians() {
    return Math.toRadians(degrees);
  }

  public static Coordinate fromDegrees(double degrees) {
    return new Coordinate(degrees);
  }

  public static Coordinate fromRadians(double radians) {
    return new Coordinate(Math.toDegrees(radians));
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Coordinate
        && ((Coordinate) o).degrees == this.degrees;
  }

  @Override
  public String toString() {
    return String.valueOf(degrees);
  }

}