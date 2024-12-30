# Kimono Backend

Kimono backend API

## Directory Structure

- `src/`: The main source code of the project
    - `main/`: Contains the application logic
        - `java/com/kimono/backend/`: Java package for the backend project
            - `config/`: Configuration-related classes (e.g., security, application setup)
            - `controllers/`: REST API controllers
            - `domain/`: JPA entities and domain objects
            - `mappers/`: Classes for mapping between entities and DTOs
            - `repositories/`: Data repository interfaces for database interactions
            - `services/`: Business logic and service layer
    - `resources/`: Configuration and resource files
        - `application.properties`: Spring Boot configuration
- `test/`: Test files for unit and integration testing
    - `java/com/kimono/backend/`: Test files corresponding to main application code
- `target/`: Compiled output and build artifacts (generated)

## Used Tools, Useful Links, and Quick Explanations

- [Spring Boot]: Framework for building backend applications in Java
- [Hibernate]: ORM for database interaction
- PostgreSQL: Relational database management system
- [Lombok]: Reduces boilerplate code in Java
- [Swagger UI]: For API documentation and testing
- [JUnit]: Unit testing framework
- [Maven]: Build and dependency management tool

[spring boot]: https://spring.io/projects/spring-boot
[hibernate]: https://hibernate.org/
[lombok]: https://projectlombok.org/
[swagger ui]: https://swagger.io/tools/swagger-ui/
[junit]: https://junit.org/junit5/
[maven]: https://maven.apache.org/

## Developing

Install dependencies with Maven and start a development server:

```bash
mvn clean install
mvn spring-boot:run
```

## Other Commands

```bash
mvn clean         # Clean up compiled files and artifacts
mvn test          # Run all tests
mvn compile       # Compile the project
mvn package       # Package the application (JAR/WAR)
```

## Testing

Unit and integration tests are written using JUnit. Run the tests using Maven:

```bash
mvn test
```

## Building

To create a production build of the application:

```bash
mvn clean package
```

The resulting JAR file can be found in the `target/` directory.

## API Documentation

The API documentation is automatically generated using Swagger and can be accessed at:

```
http://localhost:8080/swagger-ui.html
```

## Configuration

The application properties are managed in the `application.properties` file under the `resources/` directory. Update the database connection and other settings as required.

