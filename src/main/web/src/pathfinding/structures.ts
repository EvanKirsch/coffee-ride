type Route = {
  origin: string;
  destination: string;
  stepMiles: string;
};

type CoffeeRidePlace = {
  displayName: string;
  address: string;
  lat: number;
  lng: number;
  name: string;
};

type CoffeeRideLeg = {
  origin: CoffeeRidePlace;
  destination: CoffeeRidePlace;
  stepsList: LatLng[];
  encodedPolyline: string;
};

type PathfindingResponse = {
  legs: CoffeeRideLeg[];
  encodedPolyline: string;
};

type LatLng = {
  latitude: Coordinate;
  longitude: Coordinate;
};

type Coordinate = {
  degrees: number;
};
