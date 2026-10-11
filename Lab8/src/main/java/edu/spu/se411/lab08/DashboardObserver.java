package edu.spu.se411.lab08;

import java.util.Locale;
import java.util.Objects;

/** A console dashboard that can observe either sensor type. */
public class DashboardObserver implements Observer {
    private final String name;

    public DashboardObserver(String name) {
        this.name = Objects.requireNonNull(name, "Dashboard name must be provided.");
    }

    @Override
    public void update(Subject subject) {
        if (!(subject instanceof Sensor)) {
            throw new IllegalArgumentException("DashboardObserver requires a sensor subject.");
        }
        Sensor sensor = (Sensor) subject;
        System.out.printf(Locale.US, "[Dashboard: %s] %s = %.2f %s%n",
                name, sensor.getName(), sensor.getReading(), sensor.getUnit());
    }
}
