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

post, update, and delete requests expect a json object.

id = countryIso2Code + year (e.g. US1960)

Example of json object to send with post/delete/update requests:
{
  "indicatorId": "SP.POP.GROW",
  "indicatorValue": "Population growth (annual %)",
  "countryId": "US",
  "countryValue": "United States",
  "date": 1959,
  "value": 7,
  "decimal": 0
}

