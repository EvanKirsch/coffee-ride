package org.kirsch.model;

import com.google.maps.places.v1.Place;

public class Node {

  private Place place;
  private long distanceToTerminus;
  private long distanceToStart;

  public Node(Place place, long distanceToTerminus, long distanceToStart) {
    this.place = place;
    this.distanceToTerminus = distanceToTerminus;
    this.distanceToStart = distanceToStart;
  }

  public long getDistanceToTerminus() {
    return this.distanceToTerminus;
  }

  public long getDistanceToStart() {
    return this.distanceToStart;
  }

  public Place getPlace() {
    return this.place;
  }

}