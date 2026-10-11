# Lab 08 — Observer Pattern

**Student:** Abdullah Naif Alorabi  
**Student ID:** 222110432  
**Course:** SE411 Software Construction

This Maven project implements temperature and humidity sensors as subjects.
A dashboard and a logger observe both sensors and receive updates whenever a
reading changes. The sensor superclass also supports cloning: a clone preserves
the sensor data and starts with its own empty observer list.

## Build and run

Requirements: **JDK 17 or newer** and **Maven 3.6.3 or newer**. An internet
connection is needed for the first build so Maven can download dependencies.
Run this command from the `Lab8` folder:

```bash
mvn clean package exec:java
```

This compiles the project, runs the automated tests, packages the JAR, and runs
`edu.spu.se411.lab08.App`. The normal demonstration has ten reading cycles
with a one-second delay per cycle, as in the assignment. A seeded `Random`
produces reproducible simulated readings.

To run only the tests:

```bash
mvn test
```

For the same demonstration without the delays:

```bash
mvn exec:java -Dexec.args="--no-delay"
```

Build the project before using the command above. On Windows, `run.bat` runs the
normal Maven build and demonstration and keeps the terminal open. In an IDE,
import `pom.xml` as a Maven project, select JDK 17 or newer, and run the Maven
goals `clean package exec:java`.

## Framework

The required interfaces retain their exact method signatures:

```java
public interface Observer {
    void update(Subject subject);
}

public interface Subject {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers();
}
```

| Type | Responsibility |
| --- | --- |
| `Subject` | Contract for registering, unregistering, and notifying observers. |
| `Observer` | Contract for receiving the changed subject. |
| `Sensor` | Abstract subject that implements `Subject` and `Cloneable`; shares reading, observer management, and clone logic. |
| `TemperatureSensor` | Concrete sensor using Celsius, displayed as `C`. |
| `HumiditySensor` | Concrete sensor using percentage units. |
| `DashboardObserver` | Implements `Observer` and prints updates with `System.out.printf`. |
| `LoggerObserver` | Implements `Observer`, prints updates with `System.out.printf`, and writes updates to the file log. |
| `App` | Creates both sensors and observers; demonstrates updates, unregistration, and cloning. |

`setReading()` stores a changed finite reading and calls `notifyObservers()`.
The observers read the new value from the supplied subject. Setting the same
reading again does not notify observers. Duplicate registration is ignored.
Notification iterates over a snapshot so an observer may unregister itself
without causing a concurrent modification error.

## Main demonstration

1. Create a temperature sensor and a humidity sensor.
2. Create a dashboard and logger as `Observer` references and register both to
   both sensors through the `Subject` interface.
3. Use a `while` loop for ten reading cycles. Temperature values are in
   `[20, 35)` Celsius and humidity values are in `[40, 60)` percent.
4. Unregister the dashboard from humidity; a further humidity update reaches
   only the logger.
5. Clone the temperature sensor. Changing the clone before registration prints
   no observer updates.
6. Register a new dashboard to the clone; only that dashboard receives the
   clone's next update.
7. Update the original; its original dashboard and logger receive the update,
   and the clone keeps its own independent reading.

An interrupted simulation restores the thread's interrupted status and records
the interruption in the file log. The application logs its stop event in a
`finally` block.

## Clone implementation

`Sensor.clone()` calls `super.clone()` and replaces the copied observer-list
reference with a **new empty `ArrayList`**. It leaves the original list intact.
The name, unit, and current reading are copied. Strings are immutable, and
the reading is a primitive value. Each concrete sensor overrides `clone()`
with a covariant return type, reusing the superclass implementation.

## File logging

`src/main/resources/log4j.properties` uses the SLF4J dependencies and versions
from the lab annex. Maven resolves `slf4j-log4j12` to `slf4j-reload4j`; the
relocation warning is expected.

Each execution writes `logs/App/log4j/log.out`, relative to the project folder.
It records application start and stop, logger-observer readings, and exceptions
when applicable. `Append=false` starts a fresh log for each execution. A copy
of the verified demo log is included in `evidence/`.

## Verification

`SensorTest` checks notifications from both sensor types, concrete observer
output, unchanged readings, duplicate registration, unregistration, explicit
notification, and notification-time unregistration. It also checks that clones
preserve their concrete types and data, start without observers, and maintain
independent readings and observer lists. Invalid non-finite readings must not
change state or notify observers.

The `evidence/` folder contains output captured from the program and a build
summary. `target/` and generated `logs/` are ignored by Git. Source code, Maven
configuration, documentation, tests, and evidence are the submission files.
