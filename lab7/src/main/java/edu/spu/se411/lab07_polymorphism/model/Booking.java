package edu.spu.se411.lab07_polymorphism.model;

import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

import java.time.LocalDate;

public abstract class Booking {
    private final String bookingId;
    private final String customerFullName;
    private final LocalDate dateOfTravel;
    private final String destinationCity;

    protected Booking(String bookingId, String customerFullName,
                      LocalDate dateOfTravel, String destinationCity) {
        if (bookingId == null || bookingId.isBlank()) {
            throw new IllegalArgumentException("Booking ID is required.");
        }
        if (customerFullName == null || customerFullName.isBlank()) {
            throw new IllegalArgumentException("Customer full name is required.");
        }
        if (dateOfTravel == null) {
            throw new IllegalArgumentException("Date of travel is required.");
        }
        if (destinationCity == null || destinationCity.isBlank()) {
            throw new IllegalArgumentException("Destination city is required.");
        }

        this.bookingId = bookingId;
        this.customerFullName = customerFullName;
        this.dateOfTravel = dateOfTravel;
        this.destinationCity = destinationCity;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public LocalDate getDateOfTravel() {
        return dateOfTravel;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public abstract double calculateTotalPrice()
            throws MissingInformationException, InvalidArgumentException;
}
