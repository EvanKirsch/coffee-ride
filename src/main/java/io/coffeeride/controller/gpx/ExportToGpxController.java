package io.coffeeride.controller.gpx;

import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.service.gpx.IGpxFileExporter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<Resource> buildDocument(@RequestBody List<LatLng> stepsList)
      throws ParserConfigurationException, IOException {
    String filename = exporter.buildDocument(stepsList);
    File file = new File(filename);
    Path path = Paths.get(file.getAbsolutePath());

    HttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename); // TODO - filepath should be stripped
    header.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
    header.add(HttpHeaders.PRAGMA, "no-cache");
    header.add(HttpHeaders.EXPIRES, "0");

    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

    return ResponseEntity.ok()
        .headers(header)
        .contentLength(file.length())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }

}