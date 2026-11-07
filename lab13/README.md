# Lab 13 — Spring introduction
Java 17+, Maven. Run `mvn clean test`, then `mvn spring-boot:run`.
Import as an Existing Maven Project in Eclipse.

GET /api/v1/products returns the 17 seeded products.
GET /api/v1/products/10 returns one product.
GET /api/v1/products/-1 returns **400 Bad Request** because the id must be positive.
An unknown positive id returns 404.
requests.http contains examples to reproduce in PutMan or Postman.

Product uses BigDecimal and the database uses DECIMAL(10,2) for prices. ProductRepository provides CRUD, pagination and sorting; ProductService and ProductController are connected by constructor injection. This introductory API exposes the two GET routes requested by the sheet. H2 data resets on application restart.
