package com.bridgelabz.airlinereservation.model.state;

import com.bridgelabz.airlinereservation.model.Booking;

public class SeatSelectedState implements BookingState {
    @Override
    public void next(Booking booking) {
        booking.setState(new PaymentPendingState());
    }

    @Override
    public void prev(Booking booking) {
        booking.setState(new PassengerDetailsState());
    }

    @Override
    public void printStatus() {
        System.out.println("State: SEAT_SELECTED");
    }
}
