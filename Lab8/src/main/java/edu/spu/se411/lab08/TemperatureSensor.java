package edu.spu.se411.lab08;

public class TemperatureSensor extends Sensor {
    public TemperatureSensor(String name) {
        super(name, "C", 20.0);
    }

    @Override
    public TemperatureSensor clone() {
        return (TemperatureSensor) super.clone();
    }
}
