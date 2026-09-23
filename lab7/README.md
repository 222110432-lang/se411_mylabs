# SE411 Lab 07 - Polymorphism in Java

Maven implementation of the transportation-booking scenario using inheritance and polymorphism.

## Design
- Abstract `Booking` contains the common booking information.
- `FlightBooking`, `TrainBooking`, and `CarRentalBooking` override `calculateTotalPrice()`.
- `GlobalConfig` stores shared rates and limits.
- `MissingInformationException` handles values that were never supplied.
- `InvalidArgumentException` handles supplied values outside the allowed ranges.
- `computeTotalPrice(Booking booking)` works with every booking subtype.
- SLF4J + Log4j file logging records application start/stop and exceptions.

## Run
```text
mvn clean package exec:java
```

Logs are written to:
```text
./logs/App/log4j/log.out
```
