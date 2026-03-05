export abstract class AbstractResponseHandler {

  public async handleResponse(response : Response) : Promise<void> {
    if (response.status == 200) {
      return this.onSucess(response);

    } else {
      return this.onError(response);
      
    }
  }

  protected async onError(response : Response) : Promise<void> {
    console.error("HTTP status: " + response.status + ", " + await response.text());
  }

  protected abstract onSucess(response : Response) : Promise<void>;

}