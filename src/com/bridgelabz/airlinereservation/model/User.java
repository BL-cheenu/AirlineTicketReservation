package com.bridgelabz.airlinereservation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String passportOrId;
    private String passwordHash;
    private Role role;
    private boolean isActive;
    private boolean isMfaEnabled;

    // UC2 Fields
    private Preferences preferences;
    private EmergencyContact emergencyContact;
    private List<PassengerProfile> passengerProfiles;

    public User(String id, String name, String email, String phone, LocalDate dateOfBirth, String passportOrId, String passwordHash, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.passportOrId = passportOrId;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isActive = true;
        this.isMfaEnabled = false;
        this.preferences = new Preferences();
        this.passengerProfiles = new ArrayList<>();
    }

    // UC3: Abstract permission methods
    public abstract boolean canManageUsers();
    public abstract boolean canManageFlights();
    public abstract boolean canManageAllBookings();


    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getPassportOrId() { return passportOrId; }
    public void setPassportOrId(String passportOrId) { this.passportOrId = passportOrId; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public boolean isMfaEnabled() { return isMfaEnabled; }
    public void setMfaEnabled(boolean mfaEnabled) { isMfaEnabled = mfaEnabled; }
    public Preferences getPreferences() { return preferences; }
    public void setPreferences(Preferences preferences) { this.preferences = preferences; }
    public EmergencyContact getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(EmergencyContact emergencyContact) { this.emergencyContact = emergencyContact; }
    public List<PassengerProfile> getPassengerProfiles() { return passengerProfiles; }
    public void setPassengerProfiles(List<PassengerProfile> passengerProfiles) { this.passengerProfiles = passengerProfiles; }
    public void addPassengerProfile(PassengerProfile profile) { this.passengerProfiles.add(profile); }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", preferences=" + preferences +
                ", emergencyContact=" + emergencyContact +
                ", passengerProfilesCount=" + passengerProfiles.size() +
                '}';
    }
}
