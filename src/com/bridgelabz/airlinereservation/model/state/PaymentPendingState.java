package com.bridgelabz.airlinereservation.model.state;

import com.bridgelabz.airlinereservation.model.Booking;

public class PaymentPendingState implements BookingState {
    @Override
    public void next(Booking booking) {
        // Will transition to Confirmed in a later UC
        System.out.println("Transitioning to CONFIRMED (Not fully implemented yet).");
    }

    @Override
    public void prev(Booking booking) {
        booking.setState(new SeatSelectedState());
    }

    @Override
    public void printStatus() {
        System.out.println("State: PAYMENT_PENDING");
    }
}
