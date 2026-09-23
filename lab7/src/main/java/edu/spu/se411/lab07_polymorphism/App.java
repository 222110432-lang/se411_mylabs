package edu.spu.se411.lab07_polymorphism;

import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;
import edu.spu.se411.lab07_polymorphism.model.Booking;
import edu.spu.se411.lab07_polymorphism.model.CarRentalBooking;
import edu.spu.se411.lab07_polymorphism.model.FlightBooking;
import edu.spu.se411.lab07_polymorphism.model.SeatClass;
import edu.spu.se411.lab07_polymorphism.model.TrainBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static double computeTotalPrice(Booking booking)
            throws MissingInformationException, InvalidArgumentException {
        return booking.calculateTotalPrice();
    }

    private static void printPrice(Booking booking) {
        try {
            double total = computeTotalPrice(booking);
            System.out.printf("%s (%s) total price: %.2f%n",
                    booking.getBookingId(),
                    booking.getClass().getSimpleName(),
                    total);
            logger.info("Booking {} total price calculated: {}",
                    booking.getBookingId(), total);
        } catch (MissingInformationException | InvalidArgumentException e) {
            logger.error("Could not calculate price for booking " + booking.getBookingId(), e);
        }
    }

    public static void main(String[] args) {
        logger.info("Application is starting...");

        FlightBooking flight = new FlightBooking(
                "F-101", "Customer One",
                LocalDate.of(2026, 10, 5), "Dubai", 800.0);
        flight.setLuggageWeight(15.0);

        TrainBooking train = new TrainBooking(
                "T-201", "Customer Two",
                LocalDate.of(2026, 10, 8), "Dammam", SeatClass.FIRST_CLASS);
        train.setDistanceKm(450.0);

        CarRentalBooking car = new CarRentalBooking(
                "C-301", "Customer Three",
                LocalDate.of(2026, 10, 12), "Jeddah", 180.0);
        car.setRentalDays(4);

        List<Booking> bookings = List.of(flight, train, car);
        bookings.forEach(App::printPrice);

        // MissingInformationException example: luggage weight was never provided.
        FlightBooking missingInformation = new FlightBooking(
                "F-102", "Customer Four",
                LocalDate.of(2026, 11, 1), "London", 1200.0);
        printPrice(missingInformation);

        // InvalidArgumentException example: train distance is above the 2000 km maximum.
        TrainBooking invalidDistance = new TrainBooking(
                "T-202", "Customer Five",
                LocalDate.of(2026, 11, 3), "Paris", SeatClass.STANDARD);
        invalidDistance.setDistanceKm(2500.0);
        printPrice(invalidDistance);

        logger.info("Application is stopping...");
    }
}
