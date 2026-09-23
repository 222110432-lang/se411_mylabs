package edu.spu.se411.lab07_polymorphism.model;

import edu.spu.se411.lab07_polymorphism.config.GlobalConfig;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

import java.time.LocalDate;

public class TrainBooking extends Booking {
    private final SeatClass seatClass;
    private Double distanceKm;

    public TrainBooking(String bookingId, String customerFullName,
                        LocalDate dateOfTravel, String destinationCity,
                        SeatClass seatClass) {
        super(bookingId, customerFullName, dateOfTravel, destinationCity);
        if (seatClass == null) {
            throw new IllegalArgumentException("Seat class is required.");
        }
        this.seatClass = seatClass;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    @Override
    public double calculateTotalPrice()
            throws MissingInformationException, InvalidArgumentException {
        if (distanceKm == null) {
            throw new MissingInformationException("Train distance is required.");
        }
        if (distanceKm < GlobalConfig.MIN_TRAIN_DISTANCE
                || distanceKm > GlobalConfig.MAX_TRAIN_DISTANCE) {
            throw new InvalidArgumentException(
                    "Train distance must be between 1 and 2000 km.");
        }

        double rate = seatClass == SeatClass.STANDARD
                ? GlobalConfig.TRAIN_STANDARD_RATE
                : GlobalConfig.TRAIN_FIRST_CLASS_RATE;
        return distanceKm * rate;
    }
}
