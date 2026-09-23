package edu.spu.se411.lab07_polymorphism.model;

import edu.spu.se411.lab07_polymorphism.config.GlobalConfig;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

import java.time.LocalDate;

public class CarRentalBooking extends Booking {
    private final double dailyRentalRate;
    private Integer rentalDays;

    public CarRentalBooking(String bookingId, String customerFullName,
                            LocalDate dateOfTravel, String destinationCity,
                            double dailyRentalRate) {
        super(bookingId, customerFullName, dateOfTravel, destinationCity);
        if (dailyRentalRate < 0) {
            throw new IllegalArgumentException("Daily rental rate cannot be negative.");
        }
        this.dailyRentalRate = dailyRentalRate;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    @Override
    public double calculateTotalPrice()
            throws MissingInformationException, InvalidArgumentException {
        if (rentalDays == null) {
            throw new MissingInformationException("Number of rental days is required.");
        }
        if (rentalDays < GlobalConfig.MIN_RENTAL_DAYS
                || rentalDays > GlobalConfig.MAX_RENTAL_DAYS) {
            throw new InvalidArgumentException(
                    "Number of rental days must be between 1 and 30.");
        }

        return dailyRentalRate * rentalDays;
    }
}
