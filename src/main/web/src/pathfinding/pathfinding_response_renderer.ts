import { MapRenderer } from "../map/map_renderer"
import { GpxExportButton } from "../gpx/gpx_export_button"
import { AbstractResponseHandler } from "../common/abstract_response_handler";

export class PathfindingResponseRenderer extends AbstractResponseHandler {

  static polyline: google.maps.Polyline;
  static markers: google.maps.marker.AdvancedMarkerElement[] = [];

  protected async onSucess(response : Response) : Promise<void> {
    console.log(response);
    this.clearResponse();
    const data: PathfindingResponse = await response.json();
    this.renderMarkers(data.legs);
    this.renderPolyline(data.encodedPolyline);
    const elt = this.renderRouteDetails(data.legs);
    this.addExportToGpxButton(elt, data.legs);
  }

  private addExportToGpxButton(parent : HTMLElement, legs : CoffeeRideLeg[]) {
    let steps : LatLng[] = []
    legs.forEach(leg => {
      steps = steps.concat(leg.stepsList)
    })
    const button = (new GpxExportButton).buildGpxExportButton(steps);
    parent.appendChild(button);
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

  private renderRouteDetails(legs : CoffeeRideLeg[]) : HTMLElement {
    const routeDetails = document.getElementById("route-details");
    if (!!routeDetails) {
      routeDetails.classList.add("on");
      routeDetails.classList.remove("off");
      const routeDetailsList = document.createElement("ol");
      routeDetailsList.classList.add("list-group");
      legs.forEach((place) => {
        let li = document.createElement("li");
        li.classList.add("list-group-item");
        li.innerText = place.destination.displayName + " " + place.destination.address;
        routeDetailsList.appendChild(li);
      })
      routeDetails.appendChild(routeDetailsList);
    }
    return routeDetails || document.createElement("div");
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
      routeDetails.classList.add("off");
      routeDetails.classList.remove("on");
    }
  }

}
