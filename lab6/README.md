# SE411 Lab 06 - Logging with SLF4J

Completed from the provided Lab 06 starter Maven project.

## Logging strategy
- `INFO`: application starts and ends.
- `DEBUG`: wallet creation, deposits, and withdrawals.
- `WARN`: `InsufficientFundsException` object creation.
- `ERROR`: exceptions caught by the main calling program.

The final project uses the required SLF4J API with the Log4j12 binding and a file appender configured in `src/main/resources/log4j.properties`.

## Run
```text
mvn clean package exec:java
```

Log output is written to:
```text
./logs/App/log4j/log.out
```

The required AI logging review and the applied recommendations are documented in `AI_LOGGING_REVIEW.md`.
