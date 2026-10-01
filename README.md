# Movie Info Service

A Spring Boot–based movie search service with a fast, clean architecture, built on the public **OMDb** and **TMDb** APIs.  
The system uses a Redis cache for fast response times and stores search patterns in a MySQL database for statistical purposes.

---

## Key Features

- Movie search via the **OMDb** or **TMDb** API
- Fast response times thanks to **Redis caching**
- Search patterns persisted to a **MySQL** database
- Unified response format (title, year, directors)
- Clean architecture built on SOLID principles
- **100% unit test coverage** (JUnit 5 + Mockito)

---

## Technologies

- Java 17
- Spring Boot 3
- Spring Web / WebClient
- Spring Data JPA (Hibernate)
- MySQL
- Redis
- Docker Compose
- Maven
- JUnit 5 + Mockito
- Lombok

---

## Architecture Overview

The project is organized into clean layers:

```
src/main/java/com/example/movie
├── controller       → REST endpoint
├── service          → business logic + caching + provider selection
├── dto              → unified and provider-specific DTOs
├── entity           → JPA entities
├── repository       → MySQL persistence
├── provider         → OMDb and TMDb clients
└── config           → configuration (Redis, WebClient, etc.)
```

### Applying the SOLID Principles

- **S – Single Responsibility**: each class has a single responsibility
- **O – Open/Closed**: new providers can be added easily
- **L – Liskov Substitution**: the service depends only on interfaces
- **I – Interface Segregation**: small, focused interfaces
- **D – Dependency Inversion**: high-level modules depend on abstractions

---

## REST API

### Endpoint

```
GET /movies/{title}?apiKey={omdb|tmdb}
```

### Examples

OMDb:

```
GET http://localhost:8080/movies/Avatar?apiKey=omdb
```

TMDb:

```
GET http://localhost:8080/movies/Avatar?apiKey=tmdb
```

### Sample Response

```json
{
  "movies": [
    {
      "title": "Avatar",
      "year": "2009",
      "directors": ["James Cameron"]
    }
  ]
}
```

---

## Docker Infrastructure

The project includes a `docker-compose.yml` file that starts:

- MySQL – `localhost:3306`
- Redis – `localhost:6379`

Start the containers with:

```
docker compose up -d
```

---

## Configuration

The required API keys are managed in `application.yml`:

```yaml
movie:
  omdb:
    apiKey: a8adaed6
  tmdb:
    apiKey: 03ba4d41ddaa46bbf9c4a6f64f5685bc
```

---

## Running the Application

```
mvn spring-boot:run
```

### Or after building:

```
mvn clean package
java -jar target/movie-info-service.jar
```

---

## How Caching Works

- Redis cache key format: `movies::{apiKey}_{title}`
- The cache TTL is 10 minutes by default.
