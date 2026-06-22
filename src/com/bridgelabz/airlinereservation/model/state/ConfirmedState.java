package com.bridgelabz.airlinereservation.model.state;

import com.bridgelabz.airlinereservation.model.Booking;

public class ConfirmedState implements BookingState {
    @Override
    public void next(Booking booking) {
        System.out.println("Booking is already confirmed. Next state could be CANCELLED or COMPLETED.");
    }

    @Override
    public void prev(Booking booking) {
        System.out.println("Cannot go back to payment pending. Booking is already confirmed.");
    }

    @Override
    public void printStatus() {
        System.out.println("State: CONFIRMED");
    }
}
