# wbAppSpring

Spring application using mongodb
To use the application, clone or download project, start an instance of mongodb database, open command prompt and cd to project folder, execute commands "mvn clean package", and "java - jar target/demo-0.0.1-SNAPSHOT.jar".
Or import project to eclipse, as maven project, and run it.

Endpoints:
GET
/api/data
/api/data/{id}

POST
/api/data

UPDATE
/api/data/{id}

DELETE
/api/data/{id}

post, update, and delete request expect a json object.


