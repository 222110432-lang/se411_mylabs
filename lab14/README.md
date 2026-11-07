# Lab 14 — Course CRUD API
Java 17+, Maven. Run `mvn clean test`, then `mvn spring-boot:run`.
Spring Boot 4.0.5 follows the lab sheet.

| Method | Route | Success | Errors |
|---|---|---|---|
| GET | /api/v1/courses | 200 | |
| GET | /api/v1/courses/{id} | 200 | 400 invalid id, 404 missing |
| POST | /api/v1/courses | 201 + Location | 400 invalid body, 422 duplicate id |
| PUT | /api/v1/courses/{id} | 204 | 400 invalid body/id, 404 missing |
| DELETE | /api/v1/courses/{id} | 204 | 400 invalid id, 404 missing |

Code cannot be blank; name has at least 2 characters; credits must be present and between 1 and 10; id cannot be negative. Omit id on POST to generate it, or provide an unused nonnegative id. A PUT body may omit id or match the URL id.

The tests exercise live HTTP requests against an embedded H2 database, including full CRUD, Location, validation, duplicate ids and missing resources. See requests.http for manual requests. H2 data resets on restart.
