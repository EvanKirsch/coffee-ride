export class EndpointFactory {
    static getAppServerBaseUrl(){
        return window.location.protocol + "//" + window.location.hostname + ":8080"
    }
}