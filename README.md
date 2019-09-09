# Countries APIs

Project for retrieving countries information all over the world

## Prerequisites
* [JDK 8](https://www.oracle.com/java/technologies/javase-jsp-downloads.html) or later
* [Maven](https://maven.apache.org/)

## Installation instruction

* Clone this repository.
* Go to the project.
```
cd countries
```
* Start the server:
```bash
./mvnw spring-boot:run
```
The server has been configured to be run on port 3000. Therefore, the base URL is http://localhost:3000/

## API endpoints:
```bash
[GET] /country_codes (Get all the country codes)
[GET] /capital/{country_code} (Get country name and capital by a country code)
```

## Try the APIs:

After starting the server. Please click one of those URLs below to try the APIs

[Retrieve all country codes](http://localhost:3000/country_codes)

[Retrieve the capital of Finland](http://localhost:3000/capital/fi)

[Retrieve the capital of Vietnam](http://localhost:3000/capital/vn)
