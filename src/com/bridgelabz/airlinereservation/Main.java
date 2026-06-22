package com.bridgelabz.airlinereservation;

import com.bridgelabz.airlinereservation.model.Role;
import com.bridgelabz.airlinereservation.model.User;
import com.bridgelabz.airlinereservation.service.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Airline Ticket Reservation System!");
        System.out.println("--- UC1: User Registration and Authentication ---");

        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. Register new passenger
            System.out.println("Registering new passenger...");
            User user = userService.registerUser("John Doe", "john@example.com", "1234567890",
                    LocalDate.of(1990, 1, 1), "PASS123", "Secret@123", Role.PASSENGER);
            
            // 2. Simulate OTP verification
            System.out.print("Enter OTP to verify email and phone: ");
            String otp = scanner.nextLine();
            userService.verifyOTP(user.getEmail(), otp);

            // 3. Login
            System.out.println("Logging in...");
            userService.login("john@example.com", "Secret@123", true);

            // 4. Update Profile
            System.out.println("Updating profile...");
            userService.updateProfile("John Doe Updated", "0987654321");
            System.out.println("Updated user details: " + userService.getLoggedInUser());

            // 5. Password Reset
            System.out.println("Resetting password...");
            userService.resetPassword("john@example.com", "NewSecret@123");
            userService.logout();

            // UC1 Login with new password
            System.out.println("Logging in with new password...");
            userService.login("john@example.com", "NewSecret@123", false);

            System.out.println("\n--- UC2: User Profile Management ---");
            // 1. Set travel & communication preferences
            System.out.println("Setting preferences...");
            userService.updatePreferences("Vegetarian", "Window", "None", true, false);

            // 2. Add emergency contact
            System.out.println("Adding emergency contact...");
            userService.updateEmergencyContact("Jane Doe", "1122334455", "Spouse");

            // 3. Add passenger profile (family member)
            System.out.println("Adding family member profile...");
            userService.addPassengerProfile("P001", "Jane Doe", LocalDate.of(1992, 5, 10), "PASS456");

            // 4. View complete profile
            userService.viewProfile();

            // UC1 Deactivate account
            System.out.println("\nDeactivating account...");
            userService.deactivateAccount();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
