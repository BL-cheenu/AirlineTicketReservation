package com.bridgelabz.airlinereservation.model;

import java.time.LocalDate;

public class Admin extends User {
    public Admin(String id, String name, String email, String phone, LocalDate dateOfBirth, String passportOrId, String passwordHash) {
        super(id, name, email, phone, dateOfBirth, passportOrId, passwordHash, Role.ADMIN);
    }

    @Override
    public boolean canManageUsers() {
        return true;
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
