import { EndpointFactory } from "../common/endpoint_factory"

export class GpxExportButton {

  public buildGpxExportButton(steps : LatLng[]) : HTMLElement {
    const { wrapper, input } = this.buildButton();
    this.addGpxExportSubmitEvent(input, steps);
    return wrapper;
  }

  private buildButton() : { wrapper: HTMLElement, input: HTMLInputElement } {
    const wrapper = document.createElement("div");
    wrapper.classList.add("gpx-button-wrapper");

    const input = document.createElement("input");
    input.setAttribute("id", "gpx-export-submit");
    input.setAttribute("type", "submit");
    input.setAttribute("value", "⬇ Download GPX");
    input.classList.add("btn", "btn-success", "gpx-export-btn");

    wrapper.appendChild(input);
    return { wrapper, input };
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
