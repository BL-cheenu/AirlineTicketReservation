package com.bridgelabz.airlinereservation.model;

import java.time.LocalDate;

public class Passenger extends User {
    public Passenger(String id, String name, String email, String phone, LocalDate dateOfBirth, String passportOrId, String passwordHash) {
        super(id, name, email, phone, dateOfBirth, passportOrId, passwordHash, Role.PASSENGER);
    }

    @Override
    public boolean canManageUsers() {
        return false;
    }

    @Override
    public boolean canManageFlights() {
        return false;
    }

    @Override
    public boolean canManageAllBookings() {
        return false;
    }
}
