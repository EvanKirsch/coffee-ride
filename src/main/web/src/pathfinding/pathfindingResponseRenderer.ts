import { MapRenderer } from "../map/mapRenderer"

export class PathfindingResponseRenderer {

  static polyline: google.maps.Polyline;
  static markers: google.maps.marker.AdvancedMarkerElement[] = [];

  async renderResponse(response : Response) {
    this.clearResponse();
    const data: PathfindingResponse = await response.json();
    this.renderMarkers(data.legs);
    this.renderPolyline(data.encodedPolyline);
    this.renderRouteDetails(data.legs);
  }

  private async renderMarkers(legs : CoffeeRideLeg[])  {
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker") as google.maps.MarkerLibrary;
    let isOrigin = true;
    legs.forEach((place) => {
      // first place do origin and destination
      if (isOrigin) {
        isOrigin = false;
        PathfindingResponseRenderer.markers.push(new AdvancedMarkerElement({
          map: MapRenderer.map,
          position: { lat: place.origin.lat, lng: place.origin.lng },
          title: place.origin.displayName,
        }));
      }

      PathfindingResponseRenderer.markers.push(new AdvancedMarkerElement({
        map: MapRenderer.map,
        position: { lat: place.destination.lat, lng: place.destination.lng },
        title: place.destination.displayName,
      }));

    });
  }

  private async renderPolyline(encodedPolyline : string) {
    const {encoding} = await google.maps.importLibrary("geometry") as google.maps.GeometryLibrary;
    const decodedPath = encoding.decodePath(encodedPolyline);
    var polyOptions = {
      path: decodedPath,
      strokeColor: "#FF0000",
      strokeOpacity: 1,
      strokeWeight: 3
    }
    PathfindingResponseRenderer.polyline = new google.maps.Polyline(polyOptions);
    PathfindingResponseRenderer.polyline.setMap(MapRenderer.map);
  }

  private renderRouteDetails(legs : CoffeeRideLeg[]) {
    const routeDetails = document.getElementById("route-details");
    if (!!routeDetails) {
      routeDetails.classList.add("on")
      routeDetails.classList.remove("off")
      const routeDetailsList = document.createElement("ol");
      routeDetailsList.classList.add("float-right");
      legs.forEach((place) => {
        let words = document.createElement("li");
        words.innerText = place.destination.displayName + " " + place.destination.address;
        routeDetailsList.appendChild(words);
      })
      routeDetails.appendChild(routeDetailsList);
    }
  }

  private clearResponse() {
    if (PathfindingResponseRenderer.polyline != undefined) {
      PathfindingResponseRenderer.polyline.setMap(null);
    }

    PathfindingResponseRenderer.markers.forEach(elt => elt.map = null);
    PathfindingResponseRenderer.markers = [];

    const routeDetails = document.getElementById("route-details");
    if (!!routeDetails) {
      routeDetails.innerHTML = "";
      routeDetails.classList.add("off")
      routeDetails.classList.remove("on")
    }
  }

}
