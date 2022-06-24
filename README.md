# Restful-Shapes
 
This is a REST service that handles shapes (currently just the circle and square). This service will support creating, retrieving, updating, and deleting those shapes from the database. This was done in Java with Spring Boot.

Endpoints:
o /shape : Should receive a body request to create a shape;
o /shape/{id} : Should receive a body request to update an existing shape;
o /shape/{id} : Should delete an existing shape whose id equals {id};
o /shapes : Should return a Json containing a list of all Shapes created in the application;
o /shape/{id} : Should return a json describing the Shape whose id equals {id};
o /shape/area/{id} : Should return the calculated area for the Shape whose id equals {id}. 
o /shape/perimeter/{id} : Should return the calculated perimeter for the Shape whose id equals {id}. 

Examples of input used to create shapes:
* {"type":"circle", "radius":3}
* {"type":"square", "side":7}

The URL is the default (localhost:8080/) - a request would look something like this:
```
$ curl -X POST localhost:8080/shape -H 'Content-type:application/json' -d {"type":"circle", "radius":3}
```
