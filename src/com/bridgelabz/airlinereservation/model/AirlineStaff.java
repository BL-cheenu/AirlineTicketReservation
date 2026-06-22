package com.bridgelabz.airlinereservation.model;

import java.time.LocalDate;

public class AirlineStaff extends User {
    public AirlineStaff(String id, String name, String email, String phone, LocalDate dateOfBirth, String passportOrId, String passwordHash) {
        super(id, name, email, phone, dateOfBirth, passportOrId, passwordHash, Role.AIRLINE_STAFF);
    }

    @Override
    public boolean canManageUsers() {
        return false;
    }

    @Override
    public boolean canManageFlights() {
        return true;
    }

    @Override
    public boolean canManageAllBookings() {
        return true;
    }
}
