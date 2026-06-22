package com.bridgelabz.airlinereservation.service;

import com.bridgelabz.airlinereservation.exception.UserException;
import com.bridgelabz.airlinereservation.model.Role;
import com.bridgelabz.airlinereservation.model.User;
import com.bridgelabz.airlinereservation.util.OTPGenerator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Base64;

public class UserService {
    private Map<String, User> userDatabase = new HashMap<>();
    private Map<String, String> otpStorage = new HashMap<>();
    private User loggedInUser = null;

    // Password encryption (Simulated using Base64 for simplicity)
    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public User registerUser(String name, String email, String phone, LocalDate dateOfBirth, String passportOrId, String password, Role role) throws UserException {
        if (userDatabase.values().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            throw new UserException("User with this email already exists.");
        }
        
        String id = UUID.randomUUID().toString();
        String hashedPassword = encryptPassword(password);
        User user = new User(id, name, email, phone, dateOfBirth, passportOrId, hashedPassword, role);
        
        userDatabase.put(id, user);
        
        // Generate and send OTP for verification
        String otp = OTPGenerator.generateOTP();
        otpStorage.put(email, otp);
        System.out.println("OTP sent to email (" + email + ") and phone (" + phone + "): " + otp);
        
        return user;
    }

    public boolean verifyOTP(String email, String otp) throws UserException {
        if (!otpStorage.containsKey(email)) {
            throw new UserException("No pending OTP verification for this email.");
        }
        if (otpStorage.get(email).equals(otp)) {
            otpStorage.remove(email);
            System.out.println("Email and mobile verified successfully.");
            return true;
        }
        throw new UserException("Invalid OTP.");
    }

    public void login(String email, String password, boolean rememberMe) throws UserException {
        User user = userDatabase.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new UserException("Invalid email or password."));

        if (!user.isActive()) {
            throw new UserException("Account is deactivated.");
        }

        if (!user.getPasswordHash().equals(encryptPassword(password))) {
            throw new UserException("Invalid email or password.");
        }

        if (user.isMfaEnabled()) {
            String mfaOtp = OTPGenerator.generateOTP();
            System.out.println("MFA OTP sent: " + mfaOtp);
            // Simulating MFA input step logic would be handled by caller
        }

        this.loggedInUser = user;
        System.out.println("User logged in successfully as " + user.getRole() + ". Remember Me: " + rememberMe);
    }

    public void logout() {
        this.loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    public void resetPassword(String email, String newPassword) throws UserException {
        User user = userDatabase.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new UserException("User not found."));

        user.setPasswordHash(encryptPassword(newPassword));
        System.out.println("Password reset successful for " + email);
    }

    public void updateProfile(String name, String phone) throws UserException {
        if (loggedInUser == null) throw new UserException("Must be logged in to update profile.");
        loggedInUser.setName(name);
        loggedInUser.setPhone(phone);
        System.out.println("Profile updated successfully.");
    }

    public void deactivateAccount() throws UserException {
        if (loggedInUser == null) throw new UserException("Must be logged in to deactivate account.");
        loggedInUser.setActive(false);
        this.loggedInUser = null;
        System.out.println("Account deactivated.");
    }
    
    // UC2 Profile Management
    public void updatePreferences(String mealType, String seatPreference, String specialAssistance, boolean emailNotifications, boolean smsNotifications) throws UserException {
        if (loggedInUser == null) throw new UserException("Must be logged in to update preferences.");
        loggedInUser.getPreferences().setMealType(mealType);
        loggedInUser.getPreferences().setSeatPreference(seatPreference);
        loggedInUser.getPreferences().setSpecialAssistance(specialAssistance);
        loggedInUser.getPreferences().setEmailNotifications(emailNotifications);
        loggedInUser.getPreferences().setSmsNotifications(smsNotifications);
        System.out.println("Preferences updated successfully.");
    }

    public void updateEmergencyContact(String name, String phone, String relation) throws UserException {
        if (loggedInUser == null) throw new UserException("Must be logged in to update emergency contact.");
        com.bridgelabz.airlinereservation.model.EmergencyContact contact = new com.bridgelabz.airlinereservation.model.EmergencyContact(name, phone, relation);
        loggedInUser.setEmergencyContact(contact);
        System.out.println("Emergency contact updated successfully.");
    }

    public void addPassengerProfile(String profileId, String name, LocalDate dateOfBirth, String passportOrId) throws UserException {
        if (loggedInUser == null) throw new UserException("Must be logged in to add passenger profile.");
        com.bridgelabz.airlinereservation.model.PassengerProfile profile = new com.bridgelabz.airlinereservation.model.PassengerProfile(profileId, name, dateOfBirth, passportOrId);
        loggedInUser.addPassengerProfile(profile);
        System.out.println("Passenger profile added successfully.");
    }

    public void viewProfile() throws UserException {
        if (loggedInUser == null) throw new UserException("Must be logged in to view profile.");
        System.out.println("--- User Profile ---");
        System.out.println(loggedInUser.toString());
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
