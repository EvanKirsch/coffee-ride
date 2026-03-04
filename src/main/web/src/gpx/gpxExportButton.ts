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
    elt.addEventListener("click", function(e) {
      e.preventDefault();
      fetch(EndpointFactory.getAppServerBaseUrl() + "gpx",{
        method:"PUT",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(steps)
      }).then(response => response.blob())
        .then(blob => {
           const url = window.URL.createObjectURL(blob);
           const a = document.createElement('a');
           a.href = url;
           a.download = "coffee-ride-route.gpx";
           document.body.appendChild(a);
           a.click();
           a.remove();
       })
    })
  }

}
