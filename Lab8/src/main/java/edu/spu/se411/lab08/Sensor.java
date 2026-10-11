package edu.spu.se411.lab08;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Reuses registration, notification, reading updates, and cloning logic. */
public abstract class Sensor implements Subject, Cloneable {
    private final String name;
    private final String unit;
    private double reading;
    private List<Observer> observers = new ArrayList<>();

    protected Sensor(String name, String unit, double initialReading) {
        this.name = Objects.requireNonNull(name, "Sensor name must be provided.");
        this.unit = Objects.requireNonNull(unit, "Sensor unit must be provided.");
        requireFiniteReading(initialReading);
        this.reading = initialReading;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getReading() {
        return reading;
    }

    public int getObserverCount() {
        return observers.size();
    }

    public void setReading(double reading) {
        requireFiniteReading(reading);
        if (Double.compare(this.reading, reading) != 0) {
            this.reading = reading;
            notifyObservers();
        }
    }

    @Override
    public void register(Observer observer) {
        Objects.requireNonNull(observer, "Observer must be provided.");
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // A snapshot allows an observer to unregister during its update.
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update(this);
        }
    }

    @Override
    public Sensor clone() {
        try {
            Sensor copy = (Sensor) super.clone();
            // A clone keeps sensor data but starts with its own empty observer list.
            copy.observers = new ArrayList<>();
            return copy;
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError("Sensor implements Cloneable.", exception);
        }
    }

    private static void requireFiniteReading(double reading) {
        if (!Double.isFinite(reading)) {
            throw new IllegalArgumentException("Sensor reading must be finite.");
        }
    }
}
