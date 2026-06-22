package com.bridgelabz.airlinereservation;

import com.bridgelabz.airlinereservation.model.*;
import com.bridgelabz.airlinereservation.service.UserService;
import com.bridgelabz.airlinereservation.service.FlightService;
import com.bridgelabz.airlinereservation.service.BookingService;

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

            // Moved deactivate to end

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

            System.out.println("\n--- UC9: Booking Creation (State Pattern) ---");
            if (!searchResults.isEmpty() && userService.getLoggedInUser() != null) {
                BookingService bookingService = new BookingService();
                Flight selectedFlight = searchResults.get(0);
                
                System.out.println("Initiating booking for flight " + selectedFlight.getFlightNumber() + "...");
                Booking booking = bookingService.initiateBooking(selectedFlight, userService.getLoggedInUser());
                booking.printState(); // State: INITIATED
                
                bookingService.proceedToPassengerDetails(booking);
                booking.printState(); // State: PASSENGER_DETAILS
                
                System.out.println("Adding new passenger...");
                PassengerProfile pax1 = new PassengerProfile("PAX1", "John Doe", LocalDate.of(1990,1,1), "P123");
                pax1.setFrequentFlyerNumber("FF12345");
                bookingService.addPassengerToBooking(booking, pax1);
                bookingService.updatePassengerPreferences(booking, "PAX1", "Vegetarian", "Wheelchair");
                
                System.out.println("Linking existing profile to booking...");
                // Note: In UC2 we added P001 to the user profile
                bookingService.linkExistingPassengerProfile(booking, userService.getLoggedInUser(), "P001");
                
                bookingService.proceedToSeatSelection(booking);
                booking.printState(); // State: SEAT_SELECTED
                
                bookingService.proceedToPayment(booking);
                booking.printState(); // State: PAYMENT_PENDING

                System.out.println("\n--- UC15 & UC16: Payment Processing Flow & Validation ---");
                com.bridgelabz.airlinereservation.service.payment.PaymentService paymentService = new com.bridgelabz.airlinereservation.service.payment.PaymentService();
                com.bridgelabz.airlinereservation.service.payment.PaymentMethod payment = new com.bridgelabz.airlinereservation.service.payment.UPIPayment("user@upi");
                
                String txnId = paymentService.processBookingPayment(booking, payment, "SAVE10");
                System.out.println("Booking Payment processed successfully with Txn ID: " + txnId);

                System.out.println("\n--- UC11: Booking Confirmation ---");
                bookingService.confirmBooking(booking);
                booking.printState(); // State: CONFIRMED

                System.out.println("\n--- UC12: Booking Retrieval and Display ---");
                String pnr = booking.getPnr();
                System.out.println("Retrieving booking by PNR: " + pnr);
                Optional<Booking> retrievedBooking = bookingService.getBookingByPnr(pnr);
                retrievedBooking.ifPresent(bookingService::displayBookingDetails);
                
                System.out.println("\nGenerating E-Ticket...");
                bookingService.generateETicketPDF(booking);

                System.out.println("\n--- UC13: Booking History Management ---");
                bookingService.displayBookingHistory(userService.getLoggedInUser());

                System.out.println("\n--- UC18: Booking Modification - Flight Change ---");
                Map<TravelClass, Integer> altSeats = new java.util.HashMap<>();
                altSeats.put(TravelClass.ECONOMY, 50);
                Flight alternateFlight = new Flight("FL999", "AlternateAir", "NYC", "LAX", LocalDate.now().plusDays(2).atTime(15,0), LocalDate.now().plusDays(2).atTime(18,0), 180, 6000.0, 100, altSeats);
                bookingService.changeFlight(booking, alternateFlight);

                System.out.println("\n--- UC19: Booking Modification - Passenger Details ---");
                bookingService.modifyPassengerName(booking, "P123", "Johnathan Doe");

                System.out.println("\n--- UC20: Booking Modification - Seat Change ---");
                bookingService.changeSeat(booking, "P123", "14B");

                System.out.println("\n--- UC21, UC22, UC23: Booking Cancellation ---");
                com.bridgelabz.airlinereservation.service.CancellationService cancellationService = new com.bridgelabz.airlinereservation.service.CancellationService(paymentService);
                cancellationService.displayCancellationPolicy();
                
                // Test Partial Cancellation first
                cancellationService.cancelPartialBooking(booking, "P123");
                
                // Test Full Cancellation
                cancellationService.cancelFullBooking(booking);

                System.out.println("\n--- UC24, UC25, UC26: Flight Management ---");
                Flight newFlight = new Flight("FL777", "TestAir", "NYC", "SFO", LocalDate.now().plusDays(5).atTime(10,0), LocalDate.now().plusDays(5).atTime(14,0), 240, 5000.0, 150, new java.util.HashMap<>());
                flightService.addFlight(newFlight);
                
                newFlight.setPrice(4500.0);
                flightService.updateFlight("FL777", newFlight);
                
                List<Flight> activeFlights = flightService.getFlightsByStatus(FlightStatus.SCHEDULED);
                System.out.println("Active SCHEDULED flights count: " + activeFlights.size());
                
                flightService.removeFlight("FL777");

                System.out.println("\n--- UC27, UC28: Airport Management ---");
                com.bridgelabz.airlinereservation.service.AirportService airportService = new com.bridgelabz.airlinereservation.service.AirportService();
                Airport jfk = new Airport("JFK", "John F. Kennedy International", "New York", "USA");
                airportService.addAirport(jfk);
                airportService.addAirport(new Airport("LAX", "Los Angeles International", "Los Angeles", "USA"));
                
                List<Airport> searchResultsAirport = airportService.searchAirport("Los Angeles");
                System.out.println("Search results for 'Los Angeles': " + searchResultsAirport.size() + " found.");

                System.out.println("\n--- UC29: Priority Booking (PriorityQueue) ---");
                com.bridgelabz.airlinereservation.service.PriorityBookingService pbs = new com.bridgelabz.airlinereservation.service.PriorityBookingService();
                
                com.bridgelabz.airlinereservation.model.User adminUser = userService.registerUser("admin1", "admin@test.com", "pass", null, null, null, com.bridgelabz.airlinereservation.model.Role.ADMIN);
                Booking adminBooking = new Booking(selectedFlight, adminUser);
                Booking paxBooking = booking; // the one we made earlier (Passenger)
                
                pbs.addBookingToQueue(paxBooking);
                pbs.addBookingToQueue(adminBooking);
                
                pbs.processQueue();

            } else {
                System.out.println("No flights found or no user logged in to test booking.");
            }

            // UC1 Deactivate account
            System.out.println("\nDeactivating account...");
            userService.deactivateAccount();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
