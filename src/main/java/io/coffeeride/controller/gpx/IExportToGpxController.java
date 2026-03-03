package io.coffeeride.controller.gpx;

import io.coffeeride.model.gcs.LatLng;
import java.io.File;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

public interface IExportToGpxController {

  File buildDocument(List<LatLng> stepsList) throws ParserConfigurationException;

}