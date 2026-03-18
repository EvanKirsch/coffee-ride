# [Coffeeride.io](https://coffeeride.io/) :coffee::bicyclist:
Passion project I use to design my bike rides around coffee stops. 

## Table of Contents
- [Tooling](#tooling)
- [Build and Run](#build-and-run)
- [Endpoints](#endpoints)
- [Prototypes](#prototypes)

## Tooling
For package dependencies see pom.xml and package.json.
- java 17+
- maven 3.9+
- node 22+
- npm 10.9+

## Build and Run
The application is currently built as a single module with the command `mvn clean install`
Client side resources are compiled to static resources and copied into the jar containing the server side services.

The appliction is served by spring-boots built in tomcat server. After running `java -jar target/coffee-ride-1.0-SNAPSHOT.jar` the applciation will be avaliable on `localhost:8080`.
```bash
# Build into jar
mvn clean install

# Run locally
java -jar target/coffee-ride-1.0-SNAPSHOT.jar
```

## Endpoints
### /pathfinding
```bash
curl -X PUT -d '{
  "origin":"Milwaukee, WI",
  "destination":"Burlington, WI",
  "stepMiles":"10"
}' -H 'Content-Type: application/json' coffeeride.io/pathfinding
```

## Prototypes
![prototype 6 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_6.png)
![prototype 5 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_5.png)
![prototype 4 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_4.png)
![prototype 3 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_3.png)
![prototype 2 screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype_2.png)
![prototype screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype.png)
