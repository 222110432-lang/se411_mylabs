package edu.spu.se411.lab07_polymorphism.model;

import edu.spu.se411.lab07_polymorphism.config.GlobalConfig;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

import java.time.LocalDate;

public class FlightBooking extends Booking {
    private final double baseTicketPrice;
    private Double luggageWeight;

    public FlightBooking(String bookingId, String customerFullName,
                         LocalDate dateOfTravel, String destinationCity,
                         double baseTicketPrice) {
        super(bookingId, customerFullName, dateOfTravel, destinationCity);
        if (baseTicketPrice < 0) {
            throw new IllegalArgumentException("Base ticket price cannot be negative.");
        }
        this.baseTicketPrice = baseTicketPrice;
    }

    public void setLuggageWeight(double luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    @Override
    public double calculateTotalPrice()
            throws MissingInformationException, InvalidArgumentException {
        if (luggageWeight == null) {
            throw new MissingInformationException("Luggage weight is required.");
        }
        if (luggageWeight < GlobalConfig.MIN_LUGGAGE_WEIGHT
                || luggageWeight > GlobalConfig.MAX_LUGGAGE_WEIGHT) {
            throw new InvalidArgumentException(
                    "Luggage weight must be between 0 and 40 kg.");
        }

        return baseTicketPrice + luggageWeight * GlobalConfig.EXTRA_LUGGAGE_RATE;
    }
}
