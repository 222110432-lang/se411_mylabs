package edu.spu.se411.lab08;

public class HumiditySensor extends Sensor {
    public HumiditySensor(String name) {
        super(name, "%", 40.0);
    }

    @Override
    public HumiditySensor clone() {
        return (HumiditySensor) super.clone();
    }
}
