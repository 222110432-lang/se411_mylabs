package edu.spu.se411.lab08;

import java.util.Locale;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Demonstrates sensor updates, unregistration, and independent cloning. */
public final class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private App() {
    }

    public static void main(String[] args) {
        LOGGER.info("Application is starting...");
        try {
            TemperatureSensor temperature = new TemperatureSensor("Temperature sensor");
            HumiditySensor humidity = new HumiditySensor("Humidity sensor");
            Observer dashboard = new DashboardObserver("Main dashboard");
            Observer readingLogger = new LoggerObserver("Main logger");

            registerObservers(temperature, dashboard, readingLogger);
            registerObservers(humidity, dashboard, readingLogger);

            System.out.println("Lab 08 - Observer Pattern");
            long delayMillis = args.length > 0 && "--no-delay".equals(args[0]) ? 0L : 1000L;
            simulateReadings(temperature, humidity, delayMillis);
            demonstrateUnregistration(humidity, dashboard);
            demonstrateCloning(temperature);
            System.out.println("\nFile log: logs/App/log4j/log.out");
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Sensor simulation was interrupted.", exception);
            System.out.println("Sensor simulation was interrupted.");
        } catch (RuntimeException exception) {
            LOGGER.error("Sensor monitoring failed.", exception);
            throw exception;
        } finally {
            LOGGER.info("Application is stopping...");
        }
    }

    private static void registerObservers(Subject sensor, Observer dashboard,
                                          Observer readingLogger) {
        sensor.register(dashboard);
        sensor.register(readingLogger);
    }

    private static void simulateReadings(TemperatureSensor temperature,
                                         HumiditySensor humidity, long delayMillis)
            throws InterruptedException {
        Random random = new Random(411L);
        int iteration = 0;
        while (iteration < 10) {
            System.out.println("\nReading cycle " + (iteration + 1));
            temperature.setReading(20.0 + random.nextDouble() * 15.0);
            humidity.setReading(40.0 + random.nextDouble() * 20.0);
            Thread.sleep(delayMillis);
            iteration++;
        }
    }

    private static void demonstrateUnregistration(HumiditySensor humidity,
                                                   Observer dashboard) {
        System.out.println("\nUnregistration: only the logger receives the next humidity update.");
        humidity.unregister(dashboard);
        humidity.setReading(65.0);
    }

    private static void demonstrateCloning(TemperatureSensor original) {
        TemperatureSensor copy = original.clone();
        System.out.println("\nCloning: the copy starts without observers.");
        System.out.printf(Locale.US, "Original observers: %d; clone observers: %d%n",
                original.getObserverCount(), copy.getObserverCount());

        System.out.println("Updating the unobserved clone produces no observer messages.");
        copy.setReading(30.0);

        copy.register(new DashboardObserver("Clone dashboard"));
        System.out.println("Updating the clone notifies only its own dashboard.");
        copy.setReading(31.0);

        System.out.println("Updating the original notifies its original dashboard and logger.");
        original.setReading(26.0);
        System.out.printf(Locale.US, "Independent readings: original = %.2f C; clone = %.2f C%n",
                original.getReading(), copy.getReading());
    }
}
