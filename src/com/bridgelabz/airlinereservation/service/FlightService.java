package com.bridgelabz.airlinereservation.service;

import com.bridgelabz.airlinereservation.model.Flight;
import com.bridgelabz.airlinereservation.model.FlightSearchCriteria;
import com.bridgelabz.airlinereservation.model.TravelClass;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();

    public FlightService() {
        // Seed some sample data for testing
        Map<TravelClass, Integer> seats1 = new HashMap<>();
        seats1.put(TravelClass.ECONOMY, 100);
        seats1.put(TravelClass.BUSINESS, 20);

        Map<TravelClass, Integer> seats2 = new HashMap<>();
        seats2.put(TravelClass.ECONOMY, 150);
        seats2.put(TravelClass.BUSINESS, 30);

        flights.add(new Flight("AI101", "Air India", "DEL", "BOM", LocalDateTime.now().plusDays(1).withHour(8).withMinute(0), LocalDateTime.now().plusDays(1).withHour(10).withMinute(0), 120, 5000.0, 0, seats1));
        flights.add(new Flight("AI102", "Air India", "DEL", "BOM", LocalDateTime.now().plusDays(1).withHour(18).withMinute(0), LocalDateTime.now().plusDays(1).withHour(20).withMinute(0), 120, 5500.0, 0, seats1));
        flights.add(new Flight("IN201", "Indigo", "DEL", "BOM", LocalDateTime.now().plusDays(1).withHour(9).withMinute(0), LocalDateTime.now().plusDays(1).withHour(11).withMinute(30), 150, 4500.0, 0, seats2));
        flights.add(new Flight("SJ301", "SpiceJet", "DEL", "BOM", LocalDateTime.now().plusDays(1).withHour(12).withMinute(0), LocalDateTime.now().plusDays(1).withHour(15).withMinute(0), 180, 4000.0, 1, seats2));
    }

    // UC4: Flight Search Operations
    public List<Flight> searchFlights(FlightSearchCriteria criteria) {
        return flights.stream()
                .filter(f -> f.getSourceAirport().equalsIgnoreCase(criteria.getSource()))
                .filter(f -> f.getDestinationAirport().equalsIgnoreCase(criteria.getDestination()))
                .filter(f -> f.getDepartureTime().toLocalDate().equals(criteria.getDepartureDate()))
                .filter(f -> f.getAvailableSeats().getOrDefault(criteria.getTravelClass(), 0) >= criteria.getPassengers())
                // Apply optional filters
                .filter(f -> criteria.getPreferredAirline() == null || f.getAirline().equalsIgnoreCase(criteria.getPreferredAirline()))
                .filter(f -> criteria.getMaxStops() == null || f.getStops() <= criteria.getMaxStops())
                .filter(f -> criteria.getMaxPrice() == null || f.getPrice() <= criteria.getMaxPrice())
                .filter(f -> criteria.getMinDepartureTime() == null || !f.getDepartureTime().toLocalTime().isBefore(criteria.getMinDepartureTime()))
                .filter(f -> criteria.getMaxDepartureTime() == null || !f.getDepartureTime().toLocalTime().isAfter(criteria.getMaxDepartureTime()))
                .collect(Collectors.toList());
    }

    public void sortFlightsByPrice(List<Flight> searchResults) {
        searchResults.sort(Comparator.comparingDouble(Flight::getPrice));
    }

    public void sortFlightsByDuration(List<Flight> searchResults) {
        searchResults.sort(Comparator.comparingInt(Flight::getDurationMinutes));
    }

    public void sortFlightsByDepartureTime(List<Flight> searchResults) {
        searchResults.sort(Comparator.comparing(Flight::getDepartureTime));
    }

    // UC5: Flight Information Display
    public void displayFlights(List<Flight> searchResults) {
        if (searchResults.isEmpty()) {
            System.out.println("No flights found matching criteria.");
            return;
        }
        System.out.println("--- Available Flights ---");
        for (Flight f : searchResults) {
            System.out.println(f.getFlightNumber() + " | " + f.getAirline() + " | " + f.getSourceAirport() + "->" + f.getDestinationAirport() 
                    + " | Dep: " + f.getDepartureTime() + " | Arr: " + f.getArrivalTime() 
                    + " | Duration: " + f.getDurationMinutes() + " mins | Stops: " + f.getStops() 
                    + " | Price: Rs " + f.getPrice() + " | Status: " + f.getStatus());
        }
    }

    public void displayFlightDetails(Flight flight) {
        System.out.println("\n--- Flight Details ---");
        System.out.println("Flight: " + flight.getFlightNumber() + " (" + flight.getAirline() + ")");
        System.out.println("Route: " + flight.getSourceAirport() + " to " + flight.getDestinationAirport());
        System.out.println("Schedule: " + flight.getDepartureTime() + " - " + flight.getArrivalTime());
        System.out.println("Duration: " + flight.getDurationMinutes() + " mins");
        System.out.println("Stops: " + flight.getStops());
        System.out.println("Status: " + flight.getStatus());
        System.out.println("Base Fare: Rs " + flight.getPrice());
        System.out.println("Baggage: " + flight.getBaggageAllowance());
        System.out.println("Cancellation: " + flight.getCancellationPolicy());
        System.out.println("Amenities: " + flight.getAmenities());
        System.out.println("Available Seats:");
        flight.getAvailableSeats().forEach((travelClass, count) -> System.out.println("  - " + travelClass + ": " + count));
    }

    // UC6: Advanced Search Features (Streams with groupingBy)
    public Map<String, List<Flight>> groupFlightsByAirline(List<Flight> flightsList) {
        return flightsList.stream()
                .collect(Collectors.groupingBy(Flight::getAirline));
    }

    public Map<String, Double> getAverageFareByAirline(List<Flight> flightsList) {
        return flightsList.stream()
                .collect(Collectors.groupingBy(Flight::getAirline, Collectors.averagingDouble(Flight::getPrice)));
    }

    public Optional<Flight> getCheapestFlight(List<Flight> flightsList) {
        return flightsList.stream()
                .min(Comparator.comparingDouble(Flight::getPrice));
    }

    public int aggregateAvailableSeatsAcrossClasses(Flight flight) {
        return flight.getAvailableSeats().values().stream().mapToInt(Integer::intValue).sum();
    }
}
