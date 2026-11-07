# Lab 15 — Spring CRUD with MySQL
This continues the Course API supplied in lab14_full_solution.rar. The sheet's product table is a migration example; the supplied starter's Course model and five endpoints are retained.

## Database connection
Create your Aiven MySQL service and a dedicated database named se411db. Set these environment variables locally (never commit credentials):
- DB_URL: jdbc:mysql://YOUR_HOST:YOUR_PORT/se411db?sslMode=REQUIRED
- DB_USERNAME: your database username
- DB_PASSWORD: your database password

With Java 17+ and Maven:
1. First run against the new, empty lab database: `mvn spring-boot:run -Dspring-boot.run.profiles=init`.
2. Stop the application after initialization.
3. Normal runs: `mvn spring-boot:run`.

The init profile creates the course table and inserts sample data. Use it only once; subsequent normal runs preserve records. Unlike the worksheet's DROP TABLE demonstration, the application does not erase persisted records on each restart.

MySQL uses AUTO_INCREMENT and the managed com.mysql:mysql-connector-j dependency. All connection values come from environment variables. The production application has no H2 dependency.
Use requests.http in PutMan/Postman to exercise create, list, get, update and delete; use the returned Location rather than assuming an id.

## Tests and remaining account step
`mvn test` uses an isolated H2 database in MySQL compatibility mode to test the API; it does not prove connectivity to your Aiven service. The GitHub workflow additionally runs a real MySQL service and checks CRUD and restart persistence. Aiven account creation, connection setup and the instructor's PutMan demonstration still require your account/IDE.
