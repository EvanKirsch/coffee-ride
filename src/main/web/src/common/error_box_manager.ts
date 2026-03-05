export class ErrorBoxManager {

  private static manager: ErrorBoxManager;
  private errorBoxes : HTMLElement[]; // todo - leak?

  private constructor () { 
    this.errorBoxes = [];
  }

  public static getInstance() {
    if (!!ErrorBoxManager.manager) {
      return ErrorBoxManager.manager;
    }

    ErrorBoxManager.manager = new ErrorBoxManager();
    return ErrorBoxManager.manager;
  }
  

  public toggleAllOff() {
    this.errorBoxes.forEach(elt => this.toggleOff(elt));
  }

  public toggleOff(elt : HTMLElement | null) {
    if (!!elt) {
      if (!elt.classList.contains("off")) {
        elt.classList.add("off");
      }
      if (elt.classList.contains("on")) {
        elt.classList.remove("on");
      }
      elt.innerHTML = "";
      this.unregister(elt);
    }
  }

  public toggleOn(elt : HTMLElement | null) {
    if (!!elt) {
      if (elt.classList.contains("off")) {
        elt.classList.remove("off");
      }
      if (!elt.classList.contains("on")) {
        elt.classList.add("on");
      }
      this.register(elt);
    }
  }

  private register(elt : HTMLElement) {
    this.errorBoxes.push(elt);
  }

  private unregister(elt : HTMLElement) {
    this.errorBoxes = this.errorBoxes.filter(e => e !== elt)
  }

}