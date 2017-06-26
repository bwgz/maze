# maze - calculate the shortest path between two points in a maze.

This repository contains two projects:
* maze-be - A Spring Boot web service that will return the shortest path between to points in a maze.
* maze-fe - A Polymer (web components) web application that allow the user to submit a maze to the web service.

## maze-be

### Requirements

* Java 8 Development Kit

### Build & Run

```
cd maze-be
./gradlew bootRun
```
The application is running on port 8080. You can test it with http://localhost:8080/info.

## maze-fe

### Requirements

* Bower (requires npm)
* Polymer 2

See https://www.polymer-project.org/2.0/start/install-2-0 for instructions on how to install Bower and Polymer 2.

### Build & Run

```
cd maze-fe
bower install
polymer serve
```
The application is running at http://localhost:8081.



