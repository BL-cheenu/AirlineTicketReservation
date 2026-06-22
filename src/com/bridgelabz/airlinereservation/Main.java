package com.bridgelabz.airlinereservation;

import com.bridgelabz.airlinereservation.model.*;
import com.bridgelabz.airlinereservation.service.UserService;
import com.bridgelabz.airlinereservation.service.FlightService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

            System.out.println("\n--- UC3: User Role Management ---");
            // 1. Register Admin
            User admin = userService.registerUser("Admin User", "admin@airline.com", "1112223333",
                    LocalDate.of(1985, 1, 1), "ADM001", "Admin@123", Role.ADMIN);
            
            // 2. Validate Polymorphic Permissions
            System.out.println("Admin Permissions:");
            System.out.println("Can Manage Users? " + admin.canManageUsers());
            System.out.println("Can Manage Flights? " + admin.canManageFlights());
            System.out.println("Can Manage All Bookings? " + admin.canManageAllBookings());

            User passenger2 = userService.registerUser("Passenger Two", "pass2@airline.com", "4445556666",
                    LocalDate.of(1995, 1, 1), "PASS002", "Pass@123", Role.PASSENGER);
            
            System.out.println("\nPassenger Permissions:");
            System.out.println("Can Manage Users? " + passenger2.canManageUsers());
            System.out.println("Can Manage Flights? " + passenger2.canManageFlights());
            System.out.println("Can Manage All Bookings? " + passenger2.canManageAllBookings());

            System.out.println("\n--- UC4 & UC5: Flight Search and Information Display ---");
            FlightService flightService = new FlightService();
            FlightSearchCriteria criteria = new FlightSearchCriteria("DEL", "BOM", LocalDate.now().plusDays(1));
            
            System.out.println("Searching flights DEL -> BOM for tomorrow...");
            List<Flight> searchResults = flightService.searchFlights(criteria);
            
            System.out.println("\nSorting by Price...");
            flightService.sortFlightsByPrice(searchResults);
            flightService.displayFlights(searchResults);

            if (!searchResults.isEmpty()) {
                System.out.println("\nDisplaying detailed view for the first flight:");
                flightService.displayFlightDetails(searchResults.get(0));
            }

            System.out.println("\n--- UC6: Advanced Search Features (Streams with groupingBy) ---");
            Map<String, List<Flight>> groupedByAirline = flightService.groupFlightsByAirline(searchResults);
            System.out.println("Grouped by Airline: " + groupedByAirline.keySet());

            Map<String, Double> avgFare = flightService.getAverageFareByAirline(searchResults);
            System.out.println("Average Fare by Airline: " + avgFare);

            Optional<Flight> cheapest = flightService.getCheapestFlight(searchResults);
            cheapest.ifPresent(f -> System.out.println("Cheapest Flight: " + f.getFlightNumber() + " @ Rs " + f.getPrice()));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
