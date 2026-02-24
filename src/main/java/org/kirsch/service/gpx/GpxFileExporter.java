package org.kirsch.service.gpx;

import com.google.maps.routing.v2.Route;
import io.jenetics.jpx.XMLProvider;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import io.jenetics.jpx.GPX;

@Service
public class GpxFileExporter implements IGpxFileExporter {

  // todo - add adaptor
  public Document buildDocument(List<Route> routes) throws ParserConfigurationException {
    final GPX gpx = GPX.builder()
      .addRoute(gpxRoute ->
        routes.forEach(route ->
          route.getLegsList().forEach(leg ->
            leg.getStepsList().forEach(step ->
              gpxRoute.addPoint(p -> p
                .lat(step.getStartLocation().getLatLng().getLatitude())
                .lon(step.getStartLocation().getLatLng().getLongitude())
                .build()
              )
            )
          )
        )
      ).build();

    return XMLProvider.provider()
          .documentBuilderFactory()
          .newDocumentBuilder()
          .newDocument();
  }

}