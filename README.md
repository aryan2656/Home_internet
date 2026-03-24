# Home Internet Hero

A Spring Boot web app for exploring home internet plan data from major providers. It combines Thymeleaf pages, CSV-backed plan listings, and utilities for text analysis and pattern search—useful as a demo of data structures, parsing, and web tooling in Java.

## Tech stack

- **Java 17**
- **Spring Boot 3.4.3** (Web, Thymeleaf, Data JPA)
- **Maven** (wrapper included: `./mvnw`)
- **H2** in-memory database (JPA; console enabled for local dev)
- **OpenCSV** for reading plan data
- **Jsoup** and **Selenium** for HTML parsing / browser automation (used in scraping-related code)

## Prerequisites

- JDK 17+
- Maven (optional if you use `./mvnw`)

## Run locally

From the project root:

```bash
./mvnw spring-boot:run
```

Or, with Maven installed globally:

```bash
mvn spring-boot:run
```

The app listens on **port 8082** (see `src/main/resources/application.properties`).

Open [http://localhost:8082](http://localhost:8082) in your browser.

### H2 console (development)

With the default configuration, the H2 console is enabled. Typical URL:

[http://localhost:8082/h2-console](http://localhost:8082/h2-console)

Use the JDBC URL, username, and password from `application.properties`.

### Tests

```bash
./mvnw test
```

## Main pages and routes

| Route | Description |
|--------|-------------|
| `/` | Home dashboard: aggregated internet plans from classpath CSV (see note below) |
| `/login` | Login page |
| `/rogersData` | Rogers plans from `rogersPlans.csv` |
| `/bellData` | Bell plans from `BellPlans.csv` |
| `/attData` | AT&T plans from `ATTPlans.csv` |
| `/koodoData` | Koodo plans from `koodoPlans.csv` |
| `/fidoData` | Fido plans from `fidoPlans.csv` |

### Search and text tools

These endpoints drive the search / spell-check UI (`index` template):

- `GET /search/load` — load words for analysis
- `GET /search/complete?prefix=...` — prefix-based suggestions
- `GET /search/frequency?word=...` — word frequency
- `GET /search/check?word=...` — spelling suggestions

### REST API

- `POST /searchpattern` — JSON body `{ "query": "..." }` returns matching plans (used by pattern search over plan data). CORS is configured for `http://localhost:8082` in code; adjust if you change the port or origin.

> **Note:** The commented `PlanController` under `/api/plans` is not active in the current codebase.

**Home page CSV:** The bundled file is `src/main/resources/Plans.csv`, while `LoginController` loads `plans.csv`. On case-sensitive filesystems (common on Linux and in CI), either rename the file to `plans.csv` or change the resource name in code so they match.

## Project layout (high level)

- `src/main/java/org/example/home_internet_hero/` — Spring Boot application, controllers, services, models
- `src/main/resources/templates/` — Thymeleaf HTML (e.g. `home.html`, provider-specific pages)
- `src/main/resources/*.csv` — Plan datasets loaded at runtime
- `src/main/resources/url_file/` and `url_text/` — Cached HTML/text samples used for crawling and analysis experiments
