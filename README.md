## To Do List app

ToDoList app usign Spring Boot and Thymeleaf templates.
Requirements

You need to install on your system:

    Java 8 SDK
    Docker (for PostgreSQL database) 

### Ejecución

You can run the app using the run goal from Maven's plugin on Spring Boot:

    ./mvn spring-boot:run

You can already create a jar file and run it:

    ./mvn package

Once the app is running, you can open your favourite browser and connect to:

    http://localhost:8080/login

### New Functionalities

This version (1.2.0) of the ToDoList application includes the following new functionalities:
New features have been developed using the TDD approach, specifically focusing on team management functionalities:

- 008 List of teams: Users can consult the list of existing teams and their members.

- 009 Manage Team Membership: Users can create new teams, and add or remove themselves from existing teams.

## Continuous Integration with GitHub Actions

The application is configured with GitHub Actions to perform continuous integration. All pull requests must pass integration tests before merging with the main branch.

##  PostgreSQL Database Integration

The application can now be configured to work with a PostgreSQL database, both for execution and testing. This is achieved using Spring Boot profiles.

Running with PostgreSQL

To launch the application using PostgreSQL, you first need a PostgreSQL server running on port 5432 with the username atsd, password atsd, and database atsd. You can easily run a PostgreSQL container using Docker:

Bash

    docker run -d -p 5432:5432 --name postgres-develop -e POSTGRES_USER=atsd -e POSTGRES_PASSWORD=atsd -e POSTGRES_DB=atsd postgres:13

Then, launch the application with the postgres profile:

     mvn spring-boot:run -Dspring-boot.run.profiles=postgres

Alternatively, you can run the JAR file with the postgres profile:
    
    mvn package
    java -Dspring.profiles.active=postgres -jar target/*.jar

### Integration Tests with GitHub Actions

The GitHub Actions configuration has been modified to run integration tests using a real PostgreSQL database.

### Launching Tests with PostgreSQL

To run tests against a PostgreSQL database locally, first launch a separate test database container:

    docker run -d -p 5432:5432 --name postgres-test -e POSTGRES_USER=atsd -e POSTGRES_PASSWORD=atsd -e POSTGRES_DB=atsd_test postgres:13

Then, launch the tests using the postgres profile:

    mvn -D spring.profiles.active=postgres test

