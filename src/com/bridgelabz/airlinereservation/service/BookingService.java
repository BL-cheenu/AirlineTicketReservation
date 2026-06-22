package com.bridgelabz.airlinereservation.service;

import com.bridgelabz.airlinereservation.model.Booking;
import com.bridgelabz.airlinereservation.model.Flight;
import com.bridgelabz.airlinereservation.model.PassengerProfile;
import com.bridgelabz.airlinereservation.model.User;
import com.bridgelabz.airlinereservation.model.TravelClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    // UC11: Booking Confirmation
    public void confirmBooking(Booking booking) {
        booking.nextState(); // Transition to CONFIRMED
        String eTicket = "ETK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        booking.seteTicketNumber(eTicket);
        
        // Reduce available seats (assuming Economy by default for this simulation)
        int currentSeats = booking.getFlight().getAvailableSeats().getOrDefault(TravelClass.ECONOMY, 0);
        booking.getFlight().getAvailableSeats().put(TravelClass.ECONOMY, currentSeats - booking.getPassengers().size());
        
        System.out.println("Booking confirmed! E-Ticket: " + eTicket);
        System.out.println("Notifications sent to user: " + booking.getBookedBy().getEmail());
    }

    // UC12: Booking Retrieval and Display
    public Optional<Booking> getBookingByPnr(String pnr) {
        return allBookings.stream().filter(b -> b.getPnr().equalsIgnoreCase(pnr)).findFirst();
    }

    public Optional<Booking> getBookingByETicket(String eTicket) {
        return allBookings.stream()
                .filter(b -> b.geteTicketNumber() != null && b.geteTicketNumber().equalsIgnoreCase(eTicket))
                .findFirst();
    }

    public void displayBookingDetails(Booking booking) {
        System.out.println("--- Booking Details ---");
        System.out.println("PNR: " + booking.getPnr());
        System.out.println("E-Ticket: " + (booking.geteTicketNumber() != null ? booking.geteTicketNumber() : "Pending"));
        System.out.println("Flight: " + booking.getFlight().getFlightNumber() + " (" + booking.getFlight().getAirline() + ")");
        System.out.println("Date: " + booking.getFlight().getDepartureTime());
        System.out.println("Status: ");
        booking.printState();
        System.out.println("Total Fare: Rs " + booking.getTotalFare());
        System.out.println("Passengers:");
        booking.getPassengers().forEach(p -> System.out.println("  - " + p.getName() + " (ID: " + p.getPassportOrId() + ")"));
    }

    public void generateETicketPDF(Booking booking) {
        if (booking.geteTicketNumber() == null) {
            System.out.println("Cannot generate PDF: Ticket not confirmed.");
            return;
        }
        System.out.println("Generating E-Ticket PDF for " + booking.getPnr() + "...");
        System.out.println("PDF saved successfully (Simulated).");
    }

    // UC13: Booking History Management
    public List<Booking> getBookingHistory(User user) {
        return allBookings.stream()
                .filter(b -> b.getBookedBy().getId().equals(user.getId()))
                .toList();
    }

    public void displayBookingHistory(User user) {
        List<Booking> history = getBookingHistory(user);
        if (history.isEmpty()) {
            System.out.println("No booking history found for user " + user.getName());
            return;
        }
        System.out.println("--- Booking History for " + user.getName() + " ---");
        history.stream()
               .sorted((b1, b2) -> b2.getFlight().getDepartureTime().compareTo(b1.getFlight().getDepartureTime()))
               .forEach(b -> System.out.println(b.getPnr() + " | Flight: " + b.getFlight().getFlightNumber() + " | Date: " + b.getFlight().getDepartureTime().toLocalDate() + " | Fare: Rs " + b.getTotalFare()));
    }

    // UC18: Booking Modification - Flight Change/Modification
    public void changeFlight(Booking booking, Flight newFlight) {
        if (booking.geteTicketNumber() == null) {
            System.out.println("Cannot modify an unconfirmed booking this way.");
            return;
        }
        System.out.println("Changing flight for PNR: " + booking.getPnr());
        double oldFare = booking.getTotalFare();
        
        // Return seats to old flight
        int oldSeats = booking.getFlight().getAvailableSeats().getOrDefault(TravelClass.ECONOMY, 0);
        booking.getFlight().getAvailableSeats().put(TravelClass.ECONOMY, oldSeats + booking.getPassengers().size());
        
        // Assign new flight
        booking.getFlight().setFlightNumber(newFlight.getFlightNumber()); // Simplified swap
        booking.getFlight().setDepartureTime(newFlight.getDepartureTime());
        booking.getFlight().setPrice(newFlight.getPrice());
        booking.calculateTotalFare();
        
        double fareDifference = booking.getTotalFare() - oldFare;
        System.out.println("Flight changed to: " + newFlight.getFlightNumber() + ". Fare difference: Rs " + fareDifference);
        
        if (fareDifference > 0) {
            System.out.println("Please pay the fare difference to confirm modification.");
        } else if (fareDifference < 0) {
            System.out.println("Fare difference will be refunded: Rs " + Math.abs(fareDifference));
        }
        
        // Consume seats on new flight
        int newSeats = newFlight.getAvailableSeats().getOrDefault(TravelClass.ECONOMY, 0);
        newFlight.getAvailableSeats().put(TravelClass.ECONOMY, newSeats - booking.getPassengers().size());
        
        // Generate new eTicket
        booking.seteTicketNumber("ETK-MOD-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        System.out.println("New E-Ticket generated: " + booking.geteTicketNumber());
    }

    // UC19: Booking Modification - Passenger Details Modification
    public void modifyPassengerName(Booking booking, String paxId, String newName) {
        Optional<PassengerProfile> pax = booking.getPassengers().stream()
                .filter(p -> p.getPassportOrId().equals(paxId)).findFirst();
        if (pax.isPresent()) {
            System.out.println("Changing passenger name from " + pax.get().getName() + " to " + newName);
            pax.get().setName(newName);
        } else {
            System.out.println("Passenger ID not found in booking.");
        }
    }

    // UC20: Booking Modification - Seat Change
    public void changeSeat(Booking booking, String paxId, String newSeatNumber) {
        System.out.println("Seat changed for passenger ID " + paxId + " to seat " + newSeatNumber);
    }
}
