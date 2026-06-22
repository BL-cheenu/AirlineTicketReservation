package com.bridgelabz.airlinereservation.model;

import java.time.LocalDate;

public class PassengerProfile {
    private String profileId;
    private String name;
    private LocalDate dateOfBirth;
    private String passportOrId;
    private String frequentFlyerNumber;

    public PassengerProfile(String profileId, String name, LocalDate dateOfBirth, String passportOrId) {
        this.profileId = profileId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.passportOrId = passportOrId;
    }

    // Getters and setters
    public String getProfileId() { return profileId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getPassportOrId() { return passportOrId; }
    public void setPassportOrId(String passportOrId) { this.passportOrId = passportOrId; }
    public String getFrequentFlyerNumber() { return frequentFlyerNumber; }
    public void setFrequentFlyerNumber(String frequentFlyerNumber) { this.frequentFlyerNumber = frequentFlyerNumber; }

    @Override
    public String toString() {
        return "PassengerProfile{" +
                "profileId='" + profileId + '\'' +
                ", name='" + name + '\'' +
                ", passportOrId='" + passportOrId + '\'' +
                '}';
    }
}
