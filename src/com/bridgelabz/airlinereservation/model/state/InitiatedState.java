package com.bridgelabz.airlinereservation.model.state;

import com.bridgelabz.airlinereservation.model.Booking;

public class InitiatedState implements BookingState {
    @Override
    public void next(Booking booking) {
        booking.setState(new PassengerDetailsState());
    }

    @Override
    public void prev(Booking booking) {
        System.out.println("The booking is in its root state.");
    }

    @Override
    public void printStatus() {
        System.out.println("State: INITIATED");
    }
}
