package com.bridgelabz.airlinereservation.model.state;

import com.bridgelabz.airlinereservation.model.Booking;

public interface BookingState {
    void next(Booking booking);
    void prev(Booking booking);
    void printStatus();
}
