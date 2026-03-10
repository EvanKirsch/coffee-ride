import { EndpointFactory } from "../common/endpoint_factory"
import { PathfindingResponseRenderer } from "./pathfinding_response_renderer"

export class PathfindingForm {

  async renderAutoCompleteForm() {
    await google.maps.importLibrary('places') as google.maps.PlacesLibrary;

    let formDiv = document.getElementById("submit-box");
    if (!!formDiv) {
      let form = document.createElement("form");
      form.setAttribute("id", "submit-form");

      const originElt = this.buildOrigin();
      const destElt =  this.buildDestination();
      const stepElt =  this.buildStep();
      const buttonElt =  this.buildInputButton();

      this.addPathfindingSubmitEvent(buttonElt);

      form.appendChild(originElt);
      form.appendChild(destElt);
      form.appendChild(stepElt);
      form.appendChild(buttonElt);

      formDiv.appendChild(form);
    }
  }

  private buildOrigin() : HTMLElement {
    const elt = document.createElement("div");
    elt.classList.add("form-group");

    const label = document.createElement("label");
    label.setAttribute("for", "origin");
    label.innerText = "Origin";

    const input = new google.maps.places.PlaceAutocompleteElement({});
    input.style.colorScheme = "none";
    input.setAttribute("id", "origin");

    elt.appendChild(label);
    elt.appendChild(input);

    return elt;
  }

  private buildDestination() : HTMLElement {
    const elt = document.createElement("div");
    elt.classList.add("form-group");

    const label = document.createElement("label");
    label.setAttribute("for", "destination");
    label.innerText = "Destination";

    const input = new google.maps.places.PlaceAutocompleteElement({});
    input.style.colorScheme = "none";
    input.setAttribute("id", "destination");

    elt.appendChild(label);
    elt.appendChild(input);

    return elt;
  }

  private buildStep() : HTMLElement {
    const elt = document.createElement("div");
    elt.classList.add("form-group");

    const label = document.createElement("label");
    label.setAttribute("for", "step");
    label.innerText = "Miles Between Stops";

    const input = document.createElement("input");
    input.classList.add("form-control");
    input.setAttribute("id", "step");
    input.setAttribute("type", "text");

    elt.appendChild(label);
    elt.appendChild(input);

    return elt
  }

  private buildInputButton() : HTMLElement {
    const button = document.createElement("button");
    button.setAttribute("id", "fcs-submit");
    button.setAttribute("type", "submit");
    button.classList.add("btn", "btn-primary");

    const spanWaiting = document.createElement("span");
    spanWaiting.setAttribute("id", "fcs-submit-pending");
    spanWaiting.innerText = "Find Coffee Stops";

    const spanLoading = document.createElement("span");
    spanLoading.setAttribute("id", "fcs-submit-loading");
    spanLoading.setAttribute("aria-hidden", "true");
    spanLoading.setAttribute("role", "status");
    spanLoading.classList.add("off", "spinner-border", "spinner-border-sm");
    spanLoading.innerText = "Looking for Coffee...";

    button.appendChild(spanWaiting);
    button.appendChild(spanLoading);

    return button;
  }

  private static toggleLoadingOn() {
    const loading = document.getElementById("fcs-submit-loading");
    const pending = document.getElementById("fcs-submit-pending");
    loading?.classList.add("on")
    loading?.classList.remove("off")

    pending?.classList.add("off")
    pending?.classList.remove("on")
  }

  private static toggleLoadingOff() {
    const loading = document.getElementById("fcs-submit-loading");
    const pending = document.getElementById("fcs-submit-pending");
    loading?.classList.remove("on")
    loading?.classList.add("off")

    pending?.classList.remove("off")
    pending?.classList.add("on")
  }

  private addPathfindingSubmitEvent(elt : HTMLElement) {
    elt.addEventListener("click", function(e) {
      e.preventDefault()
      PathfindingForm.toggleLoadingOn();
      const route = <Route>({
        origin: (<HTMLInputElement>document.getElementById("origin"))?.value,
        destination: (<HTMLInputElement>document.getElementById("destination"))?.value,
        stepMiles: (<HTMLInputElement>document.getElementById("step"))?.value
      })
      fetch(EndpointFactory.getAppServerBaseUrl() + "pathfinding",{
        method:"PUT",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(route)
      }).then(async (response) => {
        PathfindingForm.toggleLoadingOff();
        (new PathfindingResponseRenderer()).handleResponse(response)
      })
    })
  }

}
