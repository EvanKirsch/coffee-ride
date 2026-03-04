package io.coffeeride.service.gpx;

import io.coffeeride.model.gcs.LatLng;
import io.jenetics.jpx.GPX;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GpxFileExporter implements IGpxFileExporter {

  private static final Logger log = LoggerFactory.getLogger(GpxFileExporter.class);

  public String buildDocument(List<LatLng> stepsList) {
    final GPX gpx = GPX.builder()
        .addRoute(gpxRoute ->
            stepsList.forEach(step ->
                gpxRoute.addPoint(p -> p
                    .lat(step.getLatitude().toDegrees())
                    .lon(step.getLongitude().toDegrees())
                    .build()
                )
            )
        ).build();

    return this.writeGpxToDoc(gpx);
  }

  private String writeGpxToDoc(GPX gpx) {
    String filename = "tmp/" + UUID.randomUUID() + ".gpx";

    try {
      GPX.write(gpx, Path.of(filename)); // todo - bad

    } catch (IOException e) {
      log.error("Failed to Create GPX doc {}", e.getMessage());

    }

    return filename;
  }

}