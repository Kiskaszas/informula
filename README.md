# Movie Info Service

Egy Spring Boot alapú, gyors és tiszta architektúrájú filmkereső szolgáltatás, amely az **OMDb** és **TMDb** publikus API-kat használja.  
A rendszer Redis cache-t alkalmaz a gyors válaszidő érdekében, és MySQL adatbázisba menti a keresési mintákat statisztikai célból.

---

## Fő funkciók

- Filmkeresés **OMDb** vagy **TMDb** API használatával
- Gyors válaszidő **Redis cache** segítségével
- Keresési minták mentése **MySQL** adatbázisba
- Egységesített válaszformátum (cím, év, rendezők)
- Tiszta, SOLID alapú architektúra
- **100% unit test coverage** (JUnit 5 + Mockito)

---

## Technológiák

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

## Architektúra áttekintés

A projekt tiszta rétegekre van bontva:

src/main/java/com/example/movie
├── controller       → REST végpont
├── service          → üzleti logika + cache + API választás
├── dto              → egységesített és provider-specifikus DTO-k
├── entity           → JPA entitások
├── repository       → MySQL adattárolás
├── provider         → OMDb és TMDb kliensek
└── config           → konfigurációk (Redis, WebClient, stb.)


### SOLID elvek alkalmazása

- **S – Single Responsibility**: minden osztály egyetlen felelősséggel bír
- **O – Open/Closed**: új provider könnyen hozzáadható
- **L – Liskov Substitution**: a service csak interfészeket használ
- **I – Interface Segregation**: kis, fókuszált interfészek
- **D – Dependency Inversion**: a magas szintű modulok absztrakciókra épülnek

---

## REST API

### Végpont

- GET /movies/{title}?api={omdb|tmdb}

### Példák

OMDb:

- GET http://localhost:8080/movies/Avatar?api=omdb

TMDb:
- GET http://localhost:8080/movies/Avatar?api=tmdb

### Válasz példa

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


# Docker infrastruktúra
A projekt tartalmaz egy docker-compose.yml fájlt, amely elindítja:

- MySQL – localhost:3306
- Redis – localhost:6379

- Indítás - docker compose up -d

# Konfiguráció

A szükséges API kulcsokat az application.yml kezeli:

``
movie:
    omdb:
        apiKey: a8adaed6
    tmdb:
        apiKey: 03ba4d41ddaa46bbf9c4a6f64f5685bc
``

# Alkalmazás indítása

- mvn spring-boot:run

### Vagy build után:

```
mvn clean package
java -jar target/movie-info-service.jar
```

# Cache működése

- A Redis cache kulcsa: 
    ```
  movies::{api}_{title}
  A cache TTL alapértelmezés szerint 10 perc.
  ```
