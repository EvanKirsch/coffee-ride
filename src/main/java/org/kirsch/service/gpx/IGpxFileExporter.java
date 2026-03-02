package org.kirsch.service.gpx;

import com.google.maps.routing.v2.Route;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

public interface IGpxFileExporter {

  Document buildDocument(List<Route> routes) throws ParserConfigurationException;

}
