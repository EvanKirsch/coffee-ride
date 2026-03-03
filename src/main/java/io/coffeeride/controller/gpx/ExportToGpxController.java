package io.coffeeride.controller.gpx;

import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.service.gpx.IGpxFileExporter;
import java.io.File;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpx")
public class ExportToGpxController implements IExportToGpxController {

  private final IGpxFileExporter exporter;

  @Autowired
  public ExportToGpxController(IGpxFileExporter exporter) {
    this.exporter = exporter;
  }

  @PutMapping
  @ResponseBody
  @Override
  public File buildDocument(@RequestBody List<LatLng> stepsList)
      throws ParserConfigurationException {
    return new File(exporter.buildDocument(stepsList));
  }

}