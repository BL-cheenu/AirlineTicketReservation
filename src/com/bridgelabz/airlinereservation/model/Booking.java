package com.bridgelabz.airlinereservation.model;

import com.bridgelabz.airlinereservation.model.state.BookingState;
import com.bridgelabz.airlinereservation.model.state.InitiatedState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Booking {
    private String pnr;
    private Flight flight;
    private User bookedBy;
    private List<PassengerProfile> passengers;
    private double totalFare;
    private LocalDateTime bookingExpiry;
    private BookingState state;

    public Booking(Flight flight, User bookedBy) {
        this.pnr = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.flight = flight;
        this.bookedBy = bookedBy;
        this.passengers = new ArrayList<>();
        this.state = new InitiatedState();
        this.bookingExpiry = LocalDateTime.now().plusMinutes(15);
    }

    public void nextState() {
        state.next(this);
    }

    public void previousState() {
        state.prev(this);
    }

    public void printState() {
        state.printStatus();
    }

    public void calculateTotalFare() {
        this.totalFare = flight.getPrice() * passengers.size();
    }

    public String getPnr() { return pnr; }
    public Flight getFlight() { return flight; }
    public User getBookedBy() { return bookedBy; }
    public List<PassengerProfile> getPassengers() { return passengers; }
    public void addPassenger(PassengerProfile passenger) { this.passengers.add(passenger); }
    public double getTotalFare() { return totalFare; }
    public BookingState getState() { return state; }
    public void setState(BookingState state) { this.state = state; }
    public LocalDateTime getBookingExpiry() { return bookingExpiry; }
    
    private String eTicketNumber;
    public String geteTicketNumber() { return eTicketNumber; }
    public void seteTicketNumber(String eTicketNumber) { this.eTicketNumber = eTicketNumber; }

    @Override
    public String toString() {
        return "Booking{" +
                "pnr='" + pnr + '\'' +
                ", flight=" + flight.getFlightNumber() +
                ", totalFare=" + totalFare +
                ", passengers=" + passengers.size() +
                '}';
    }
}
