package com.bridgelabz.airlinereservation.model.state;

import com.bridgelabz.airlinereservation.model.Booking;

public class PaymentPendingState implements BookingState {
    @Override
    public void next(Booking booking) {
        booking.setState(new ConfirmedState());
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
