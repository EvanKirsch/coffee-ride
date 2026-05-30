# :coffee: [Coffeeride.io](https://coffeeride.io/) :bicyclist:
Passion project I use to design my bike rides around coffee stops.

Currently, the best way to pathfind while cycling is manually evaluating the route. Can I write software that will reasonably route a coffee rider or bicycle tourist?

## :open_file_folder: Table of Contents
- [:toolbox: Tooling](#toolbox-tooling)
- [:gear: Build and Run](#gear-build-and-run)
- [:spiral_notepad: Endpoints](#spiral_notepad-endpoints)
- [:eyes: How It Works](#eyes-how-it-works)
- [:camera: Prototypes](#camera-prototypes)

## :toolbox: Tooling
For package dependencies see pom.xml and package.json.
- java 17+
- maven 3.9+
- node 22+
- npm 10.9+

## :gear: Build and Run
The application is currently built as a single module with the command `mvn clean install`
Client side resources are compiled to static resources and copied into the jar containing the server side services.

The application is served by spring-boots built in tomcat server. After running `java -jar target/coffee-ride-1.0-SNAPSHOT.jar` the applciation will be available on `localhost:8080`.
```bash
# Build into jar
mvn clean install

# Run locally
java -jar target/coffee-ride-1.0-SNAPSHOT.jar
```

## :spiral_notepad: Endpoints
### /pathfinding
#### Example Request 
```bash
curl -X PUT -d '{
  "origin":"Milwaukee, WI",
  "destination":"Burlington, WI",
  "stepMiles":"10"
}' -H 'Content-Type: application/json' coffeeride.io/pathfinding
```
#### Example Response
```json
{
  "legs": [
    {
      "origin": {
        "displayName": "Milwaukee, WI",
        "address": "",
        "lat": 43.0389,
        "lng": -87.9065,
        "name": ""
      },
      "destination": {
        "displayName": "Anodyne Coffee Roasting Co.",
        "address": "224 W Bruce St, Milwaukee, WI 53204",
        "lat": 43.0281,
        "lng": -87.9228,
      },
      "stepsList": [
        {
          "latitude": { "degrees": 43.0389 },
          "longitude": { "degrees": -87.9065 }
        }
      ],
      "encodedPolyline": "abc123..."
    } 
  ],
  "encodedPolyline": "xyz789..."
}
```

## :eyes: How It Works

Given an origin, destination, and step distance (in miles), the app finds coffee shops along your route at regular intervals and builds a rideable path between them.

1. Geocoding - The origin and destination addresses are resolved to coordinates via the Google Geocoding API.
1. Iterative stop finding - Starting from the origin, the app repeatedly steps stepMiles forward along the straight-line path to the destination. At each step, it queries the Google Places API for coffee shops within a circular search area centered on that point. Candidates are ranked and the ideal one is chosen as the next waypoint.
1. Route computation - Once all intermediate stops are collected, the full set of waypoints is sent to the Google Routes API, which returns a real cycling route with turn-by-turn geometry.
1. Output - The response includes a rendered polyline on the map, a stop list with addresses, and an option to export the route as a GPX file for use on a bike computer.


## :camera: Prototypes
![prototype 6 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_6.png)
![prototype 5 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_5.png)
![prototype 4 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_4.png)
![prototype 3 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_3.png)
![prototype 2 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_2.png)
![prototype screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype.png)
