package io.coffeeride.controller.gpx;

import io.coffeeride.model.gcs.LatLng;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface IExportToGpxController {

  ResponseEntity<Resource> buildDocument(List<LatLng> stepsList)
      throws ParserConfigurationException, IOException;

}