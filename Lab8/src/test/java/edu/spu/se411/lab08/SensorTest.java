package edu.spu.se411.lab08;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SensorTest {
    @Test
    void bothObserversReceiveUpdatesFromBothSensorTypes() {
        TemperatureSensor temperature = new TemperatureSensor("Temperature");
        HumiditySensor humidity = new HumiditySensor("Humidity");
        RecordingObserver first = new RecordingObserver();
        RecordingObserver second = new RecordingObserver();
        temperature.register(first);
        temperature.register(second);
        humidity.register(first);
        humidity.register(second);

        temperature.setReading(25.0);
        humidity.setReading(55.0);

        assertEquals(List.of(25.0, 55.0), first.readings);
        assertEquals(List.of(25.0, 55.0), second.readings);
        assertSame(temperature, first.subjects.get(0));
        assertSame(humidity, first.subjects.get(1));
    }

    @Test
    void unchangedReadingDoesNotNotifyObservers() {
        TemperatureSensor sensor = new TemperatureSensor("Temperature");
        RecordingObserver observer = new RecordingObserver();
        sensor.register(observer);
        sensor.setReading(sensor.getReading());
        assertTrue(observer.readings.isEmpty());
        sensor.setReading(25.0);
        sensor.setReading(25.0);
        assertEquals(List.of(25.0), observer.readings);
    }

    @Test
    void unregisterStopsFurtherNotifications() {
        TemperatureSensor sensor = new TemperatureSensor("Temperature");
        RecordingObserver removed = new RecordingObserver();
        RecordingObserver remaining = new RecordingObserver();
        sensor.register(removed);
        sensor.register(remaining);
        sensor.setReading(25.0);
        sensor.unregister(removed);
        sensor.setReading(26.0);
        assertEquals(List.of(25.0), removed.readings);
        assertEquals(List.of(25.0, 26.0), remaining.readings);
    }

    @Test
    void duplicateRegistrationProducesOneNotification() {
        TemperatureSensor sensor = new TemperatureSensor("Temperature");
        RecordingObserver observer = new RecordingObserver();
        sensor.register(observer);
        sensor.register(observer);
        sensor.setReading(25.0);
        assertEquals(1, sensor.getObserverCount());
        assertEquals(List.of(25.0), observer.readings);
    }

    @Test
    void clonePreservesDataButStartsWithoutObservers() {
        TemperatureSensor original = new TemperatureSensor("Temperature");
        original.setReading(24.0);
        RecordingObserver observer = new RecordingObserver();
        original.register(observer);

        TemperatureSensor clone = original.clone();
        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getUnit(), clone.getUnit());
        assertEquals(24.0, clone.getReading());
        assertEquals(0, clone.getObserverCount());
        assertEquals(1, original.getObserverCount());

        clone.setReading(30.0);
        assertTrue(observer.readings.isEmpty());
        assertEquals(24.0, original.getReading());
    }

    @Test
    void cloneObserversAndReadingsRemainIndependentInBothDirections() {
        TemperatureSensor original = new TemperatureSensor("Temperature");
        RecordingObserver originalObserver = new RecordingObserver();
        original.register(originalObserver);
        TemperatureSensor clone = original.clone();
        RecordingObserver cloneObserver = new RecordingObserver();
        clone.register(cloneObserver);

        clone.setReading(31.0);
        original.setReading(26.0);
        assertEquals(List.of(31.0), cloneObserver.readings);
        assertEquals(List.of(26.0), originalObserver.readings);
        assertSame(clone, cloneObserver.subjects.get(0));
        assertSame(original, originalObserver.subjects.get(0));
        assertEquals(31.0, clone.getReading());
        assertEquals(26.0, original.getReading());

        clone.unregister(cloneObserver);
        original.setReading(27.0);
        assertEquals(List.of(26.0, 27.0), originalObserver.readings);
        assertEquals(1, original.getObserverCount());
        assertEquals(0, clone.getObserverCount());
    }

    @Test
    void humidityCloneRetainsItsConcreteTypeAndData() {
        HumiditySensor original = new HumiditySensor("Humidity");
        original.setReading(55.0);
        original.register(new RecordingObserver());
        HumiditySensor clone = original.clone();
        assertNotSame(original, clone);
        assertEquals(HumiditySensor.class, clone.getClass());
        assertEquals(55.0, clone.getReading());
        assertEquals("%", clone.getUnit());
        assertEquals(0, clone.getObserverCount());
    }

    @Test
    void explicitNotificationUsesTheCurrentReading() {
        TemperatureSensor sensor = new TemperatureSensor("Temperature");
        RecordingObserver observer = new RecordingObserver();
        Subject subject = sensor;
        subject.register(observer);
        subject.notifyObservers();
        assertEquals(List.of(20.0), observer.readings);
        assertSame(subject, observer.subjects.get(0));
    }

    @Test
    void observersMayUnregisterDuringNotification() {
        TemperatureSensor sensor = new TemperatureSensor("Temperature");
        Observer[] selfRemoving = new Observer[1];
        List<Double> readings = new ArrayList<>();
        selfRemoving[0] = subject -> {
            readings.add(((Sensor) subject).getReading());
            subject.unregister(selfRemoving[0]);
        };
        RecordingObserver remaining = new RecordingObserver();
        sensor.register(selfRemoving[0]);
        sensor.register(remaining);
        assertDoesNotThrow(() -> sensor.setReading(25.0));
        sensor.setReading(26.0);
        assertEquals(List.of(25.0), readings);
        assertEquals(List.of(25.0, 26.0), remaining.readings);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void invalidReadingsPreserveStateAndDoNotNotify(double reading) {
        TemperatureSensor sensor = new TemperatureSensor("Temperature");
        RecordingObserver observer = new RecordingObserver();
        sensor.register(observer);
        assertThrows(IllegalArgumentException.class, () -> sensor.setReading(reading));
        assertEquals(20.0, sensor.getReading());
        assertTrue(observer.readings.isEmpty());
    }

    @Test
    void rejectsNullObserver() {
        Subject sensor = new TemperatureSensor("Temperature");
        assertThrows(NullPointerException.class, () -> sensor.register(null));
    }

    @Test
    void concreteDashboardAndLoggerPrintSensorUpdates() {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        try (PrintStream output = new PrintStream(capturedOutput, true, StandardCharsets.UTF_8)) {
            System.setOut(output);
            TemperatureSensor sensor = new TemperatureSensor("Temperature");
            Observer dashboard = new DashboardObserver("Test dashboard");
            Observer logger = new LoggerObserver("Test logger");
            sensor.register(dashboard);
            sensor.register(logger);
            sensor.setReading(25.0);
        } finally {
            System.setOut(originalOutput);
        }
        String text = capturedOutput.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("[Dashboard: Test dashboard] Temperature = 25.00 C"));
        assertTrue(text.contains("[Logger: Test logger] Temperature = 25.00 C"));
    }

    private static class RecordingObserver implements Observer {
        private final List<Subject> subjects = new ArrayList<>();
        private final List<Double> readings = new ArrayList<>();

        @Override
        public void update(Subject subject) {
            subjects.add(subject);
            readings.add(((Sensor) subject).getReading());
        }
    }
}
