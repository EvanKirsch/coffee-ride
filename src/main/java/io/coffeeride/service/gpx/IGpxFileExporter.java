package io.coffeeride.service.gpx;

import io.coffeeride.adaptors.RouteAdaptor;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

public interface IGpxFileExporter {

  Document buildDocument(List<RouteAdaptor> routes) throws ParserConfigurationException;

}
