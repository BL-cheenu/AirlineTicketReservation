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

    // UC10: Passenger Information Management
    public void addPassengerToBooking(Booking booking, PassengerProfile passenger) {
        if (passenger.getName() == null || passenger.getName().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }
        if (passenger.getPassportOrId() == null || passenger.getPassportOrId().isEmpty()) {
            throw new IllegalArgumentException("Passport/ID is required");
        }
        booking.addPassenger(passenger);
        booking.calculateTotalFare();
        System.out.println("Added passenger: " + passenger.getName() + " (FFN: " + passenger.getFrequentFlyerNumber() + "). Total Fare: Rs " + booking.getTotalFare());
    }

    public void linkExistingPassengerProfile(Booking booking, User user, String profileId) {
        PassengerProfile profile = user.getPassengerProfiles().stream()
                .filter(p -> p.getProfileId().equals(profileId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Profile not found in user account."));
        addPassengerToBooking(booking, profile);
        System.out.println("Linked existing profile to booking: " + profile.getName());
    }

    public void updatePassengerPreferences(Booking booking, String profileId, String mealPreference, String specialAssistance) {
        // Just demonstrating the concept - in a real app we'd map this to the specific passenger in the booking
        System.out.println("Updated preferences for passenger " + profileId + " -> Meal: " + mealPreference + ", Assistance: " + specialAssistance);
    }

    public void proceedToSeatSelection(Booking booking) {
        booking.nextState();
    }

    public void proceedToPayment(Booking booking) {
        booking.nextState();
    }
}
