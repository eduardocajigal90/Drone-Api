# Drone Project

This is a Spring Boot project for developing a drone related application.

## Requirements

- Java 17
- Maven

## Setting

The project uses the following configuration properties:

server.port=8080
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=sa
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=create
spring.jpa.defer-datasource-initialization=true

## Compilation

To compile the project, run the following command:

mvn clean install

This will generate an executable JAR file in the `target` directory.

## Execution

To run the project, use the following command:

java -jar target/drone-0.0.1-SNAPSHOT.jar

Make sure you have the appropriate version of Java installed.

## Evidence

To run the project tests, use the following command:

mvn test

This will run all the unit tests and display the results in the console.

## API documentation

The API documentation is available at the following URL:

http://localhost:8080/swagger-ui.html

## Additional settings

The project uses the following dependencies:

- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Spring Boot DevTools
- H2 Database
- Lombok
- Spring Boot Starter Test
- ModelMapper
- SLF4J
- Springdoc OpenAPI UI
- Gson
- Spring Boot Starter WebFlux