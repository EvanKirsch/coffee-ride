import { MapRenderer } from "../map/mapRenderer"

export class PathfindingResponseRenderer {

  static polyline: google.maps.Polyline;
  static markers: google.maps.marker.AdvancedMarkerElement[] = [];

  async renderResponse(response) {
    this.clearResponse();
    const data: PathfindingResponse = await response.json();
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker") as google.maps.MarkerLibrary;
    const { Map } = await google.maps.importLibrary("maps") as google.maps.MapsLibrary;
    data.legs.forEach((place) => {
      PathfindingResponseRenderer.markers.push(new AdvancedMarkerElement({
        map: MapRenderer.map,
        position: { lat: place.origin.lat, lng: place.origin.lng },
        title: place.origin.displayName,
      }));
    });
    const {encoding} = (await google.maps.importLibrary("geometry")) as google.maps.GeometryLibrary;
    const decodedPath = encoding.decodePath(data.encodedPolyline);
    var polyOptions = {
      path: decodedPath,
      strokeColor: "#FF0000",
      strokeOpacity: 1,
      strokeWeight: 3
    }
    PathfindingResponseRenderer.polyline = new google.maps.Polyline(polyOptions);
    PathfindingResponseRenderer.polyline.setMap(MapRenderer.map);
  }

  private clearResponse() {
    if (PathfindingResponseRenderer.polyline != undefined) {
      PathfindingResponseRenderer.polyline.setMap(null);
    }

    PathfindingResponseRenderer.markers.forEach(elt => elt.map = null);
    PathfindingResponseRenderer.markers = [];
  }

}