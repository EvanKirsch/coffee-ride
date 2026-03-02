package io.coffeeride.adaptors;

import java.util.List;

public interface RouteAdaptor {

  List<LegAdaptor> getLegsList();

  String getEncodedPolyline();

}