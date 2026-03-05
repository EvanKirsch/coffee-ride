import { PathfindingForm } from "./pathfinding/pathfinding_form"
import { MapRenderer } from "./map/map_renderer"
import { GMapsApiLoader } from "./common/gmaps_api_loader"

const gMapsApiLoader = new GMapsApiLoader();
await gMapsApiLoader.configGMapsJsApi();

const mapRenderer = new MapRenderer();
mapRenderer.initMap();

const pathfindingForm = new PathfindingForm();
pathfindingForm.renderAutoCompleteForm();
