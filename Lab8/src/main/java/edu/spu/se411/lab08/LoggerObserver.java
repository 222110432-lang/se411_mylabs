package edu.spu.se411.lab08;

import java.util.Locale;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Prints received updates and records them in the configured file log. */
public class LoggerObserver implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerObserver.class);
    private final String name;

    public LoggerObserver(String name) {
        this.name = Objects.requireNonNull(name, "Logger observer name must be provided.");
    }

    @Override
    public void update(Subject subject) {
        if (!(subject instanceof Sensor)) {
            throw new IllegalArgumentException("LoggerObserver requires a sensor subject.");
        }
        Sensor sensor = (Sensor) subject;
        System.out.printf(Locale.US, "[Logger: %s] %s = %.2f %s%n",
                name, sensor.getName(), sensor.getReading(), sensor.getUnit());
        LOGGER.info("{} received {} = {} {}",
                name, sensor.getName(), sensor.getReading(), sensor.getUnit());
    }
}
