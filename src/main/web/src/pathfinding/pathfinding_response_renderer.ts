import { MapRenderer } from "../map/map_renderer"
import { GpxExportButton } from "../gpx/gpx_export_button"
import { AbstractResponseHandler } from "../common/abstract_response_handler";

export class PathfindingResponseRenderer extends AbstractResponseHandler {

  static polyline: google.maps.Polyline;
  static markers: google.maps.marker.AdvancedMarkerElement[] = [];

  protected async onSucess(response : Response) : Promise<void> {
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

      const header = document.createElement("div");
      header.classList.add("route-details-header");
      header.innerHTML = `<span class="route-details-title">☕ Your Route</span>`;
      routeDetails.appendChild(header);

      const routeDetailsList = document.createElement("ol");
      routeDetailsList.classList.add("list-group", "route-stop-list");

      const origin = legs[0].origin;
      routeDetailsList.appendChild(this.buildStopItem(origin.displayName, origin.address, "Start", "route-stop-origin"));

      legs.forEach((leg, i) => {
        const label = i === legs.length - 1 ? "End" : `Stop ${i + 1}`;
        routeDetailsList.appendChild(this.buildStopItem(leg.destination.displayName, leg.destination.address, label));
      });

      routeDetails.appendChild(routeDetailsList);
    }
    return routeDetails || document.createElement("div");
  }

  private buildStopItem(name: string, address: string, label: string, extraClass?: string) : HTMLElement {
    const li = document.createElement("li");
    li.classList.add("list-group-item", "route-stop-item");
    if (extraClass) li.classList.add(extraClass);

    const badge = document.createElement("span");
    badge.classList.add("route-stop-badge");
    badge.innerText = label;

    const nameEl = document.createElement("strong");
    nameEl.classList.add("route-stop-name");
    nameEl.innerText = name;

    const addressEl = document.createElement("small");
    addressEl.classList.add("route-stop-address");
    addressEl.innerText = address;

    li.appendChild(badge);
    li.appendChild(nameEl);
    li.appendChild(addressEl);
    return li;
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