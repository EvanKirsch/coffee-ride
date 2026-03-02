package io.coffeeride.service.gpx;

import io.coffeeride.adaptors.RouteAdaptor;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.XMLProvider;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class GpxFileExporter implements IGpxFileExporter {

  private static final Logger log = LoggerFactory.getLogger(GpxFileExporter.class);

  // todo - add adaptor
  public Document buildDocument(List<RouteAdaptor> routes) {
    final GPX gpx = GPX.builder()
        .addRoute(gpxRoute ->
            routes.forEach(route ->
                route.getLegsList().forEach(leg ->
                    leg.getStepsList().forEach(step ->
                        gpxRoute.addPoint(p -> p
                            .lat(step.getLatitude().toDegrees())
                            .lon(step.getLongitude().toDegrees())
                            .build()
                        )
                    )
                )
            )
        ).build();

    return this.writeGpxToDoc(gpx);
  }

  private Document writeGpxToDoc(GPX gpx) {
    Document doc = null;

    try {
      doc = XMLProvider.provider()
          .documentBuilderFactory()
          .newDocumentBuilder()
          .newDocument();
      GPX.Writer.DEFAULT.write(gpx, new DOMResult(doc));

    } catch (ParserConfigurationException | IOException e) {
      log.error("Failed to Create GPX doc {}", e.getMessage());

    }

    return doc;
  }

}