package com.bridgelabz.airlinereservation.service;

import com.bridgelabz.airlinereservation.model.Booking;
import com.bridgelabz.airlinereservation.model.Flight;
import com.bridgelabz.airlinereservation.model.PassengerProfile;
import com.bridgelabz.airlinereservation.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> allBookings = new ArrayList<>();

    // UC9: Initiate Booking
    public Booking initiateBooking(Flight flight, User user) {
        Booking booking = new Booking(flight, user);
        allBookings.add(booking);
        System.out.println("Booking initiated. PNR: " + booking.getPnr() + ". Expiry: " + booking.getBookingExpiry());
        return booking;
    }

    public void proceedToPassengerDetails(Booking booking) {
        booking.nextState();
    }

    public void addPassengerToBooking(Booking booking, PassengerProfile passenger) {
        booking.addPassenger(passenger);
        booking.calculateTotalFare();
        System.out.println("Added passenger: " + passenger.getName() + ". Total Fare: Rs " + booking.getTotalFare());
    }

    public void proceedToSeatSelection(Booking booking) {
        booking.nextState();
    }

    public void proceedToPayment(Booking booking) {
        booking.nextState();
    }
}
