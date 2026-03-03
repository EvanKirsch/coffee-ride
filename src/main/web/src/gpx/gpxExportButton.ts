import { EndpointFactory } from "../common/endpointFactory"

export class GpxExportButton {

  public buildGpxExportButton(steps : LatLng[]) : HTMLElement {
    const button = this.buildButton();
    this.addGpxExportSubmitEvent(button, steps);
    return button;
  }

  private buildButton() : HTMLElement {
    const input = document.createElement("input");
    input.setAttribute("id", "gpx-export-submit");
    input.setAttribute("type", "submit");
    input.setAttribute("value", "Download GPX File");
    input.classList.add("btn", "btn-primary");

    return input;
  }

  private addGpxExportSubmitEvent(elt : HTMLElement, steps : LatLng[]) {
    console.log(steps);
    elt.addEventListener("click", function(e) {
      e.preventDefault();
      fetch(EndpointFactory.getAppServerBaseUrl() + "gpx",{
        method:"PUT",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(steps)
      })
    })
  }

}
