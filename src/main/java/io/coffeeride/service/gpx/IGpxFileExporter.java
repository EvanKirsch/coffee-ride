package io.coffeeride.service.gpx;

import io.coffeeride.model.gcs.LatLng;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

public interface IGpxFileExporter {

  String buildDocument(List<LatLng> stepsList) throws ParserConfigurationException;

}
