package com.bridgelabz.airlinereservation.model.state;

import com.bridgelabz.airlinereservation.model.Booking;

public class PassengerDetailsState implements BookingState {
    @Override
    public void next(Booking booking) {
        if (booking.getPassengers().isEmpty()) {
            System.out.println("Cannot transition: Passenger details are missing.");
        } else {
            booking.setState(new SeatSelectedState());
        }
    }

    @Override
    public void prev(Booking booking) {
        booking.setState(new InitiatedState());
    }

    @Override
    public void printStatus() {
        System.out.println("State: PASSENGER_DETAILS");
    }
}
