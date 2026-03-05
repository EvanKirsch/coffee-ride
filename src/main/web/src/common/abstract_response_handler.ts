import { ErrorBox } from "./error_box";
import { ErrorBoxManager } from "./error_box_manager";

export abstract class AbstractResponseHandler {

  public async handleResponse(response : Response) : Promise<void> {
    if (response.status == 200) {
      ErrorBoxManager.getInstance().toggleAllOff();
      return this.onSucess(response);

    } else {
      return this.onError(response);
      
    }
  }

  protected async onError(response : Response) : Promise<void> {
    const errorMesssage = await response.text();
    console.error("HTTP status: " + response.status + ", " + errorMesssage);
    const errorBox = new ErrorBox("error-box");
    errorBox.render(errorMesssage);
  }

  protected abstract onSucess(response : Response) : Promise<void>;

}