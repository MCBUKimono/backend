# Kimono Backend

Kimono backend API

## Precondition

To run the application, ensure you have the following preconditions met:

1. A PostgreSQL database setup with the following configuration:
    - Database Name: `kimono`
    - Username: `kimono`
    - Password: `password`

### Setting up PostgreSQL Database

Follow these steps to set up the required PostgreSQL database:

1. Log in to your PostgreSQL server:
   ```bash
   psql -U postgres
   ```

2. Create a new database named `kimono`:
   ```sql
   CREATE DATABASE kimono;
   ```

3. Create a new user with the username `kimono` and password `password`:
   ```sql
   CREATE USER kimono WITH PASSWORD 'password';
   ```

4. Grant all privileges on the `kimono` database to the `kimono` user:
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE kimono TO kimono;
   ```

5. Exit the PostgreSQL prompt:
   ```bash
   \q
   ```

Your PostgreSQL database is now ready to use with the application.

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
./mvnw clean install
./mvnw spring-boot:run
```

## Testing

Unit and integration tests are written using JUnit. Run the tests using Maven:

```bash
./mvnw test
```

## Building

To create a production build of the application:

```bash
./mvnw clean package
```

The resulting JAR file can be found in the `target/` directory.

## API Documentation

The API documentation is automatically generated using Swagger and can be accessed at:

```
http://localhost:8080/swagger-ui.html
```
