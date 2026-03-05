import { ErrorBoxManager } from "./error_box_manager";

// TOOD - should probably have a manager
export class ErrorBox {

  private readonly errorBox : HTMLElement;

  constructor(targetEltId: string)  {
    const errorBox = document.getElementById(targetEltId);
    if (!!errorBox) {
      this.errorBox = errorBox;
    } else {
      throw new DOMException("target element with id not found :" + targetEltId)
    }
  }

  public render(message : string) {
    this.build(message);
    this.configure();
    ErrorBoxManager.getInstance().toggleOn(this.errorBox);
  }

  private build(message : string) {
    this.errorBox.innerText = message;
    const button = this.buildButton();
    button.addEventListener("click", function(e) {
        ErrorBoxManager.getInstance().toggleOff(this.parentElement)
    }) 
    this.errorBox.appendChild(button)
  }

  private buildButton() : HTMLElement {
    const input = document.createElement("button");
    input.setAttribute("type", "button");
    input.setAttribute("data-dismiss", "alert");
    input.setAttribute("aria-label", "Close");
    input.classList.add("close");

    const closeButtonText = document.createElement("span");
    closeButtonText.setAttribute("aria-hidden", "true");
    closeButtonText.innerHTML = "&#215;";
 
    input.appendChild(closeButtonText);

    return input;
  }

  private configure() {
    this.errorBox.setAttribute("role", "alert");
    this.errorBox.classList.add("alert", "alert-danger");
  }

}