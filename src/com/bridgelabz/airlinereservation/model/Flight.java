package com.bridgelabz.airlinereservation.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Flight {
    private String flightNumber;
    private String airline;
    private String sourceAirport;
    private String destinationAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int durationMinutes; // total duration
    private double price;
    private int stops;
    private FlightStatus status;
    private Map<TravelClass, Integer> availableSeats;
    
    // Additional UC5 attributes
    private String baggageAllowance;
    private String cancellationPolicy;
    private String amenities;

    public Flight(String flightNumber, String airline, String sourceAirport, String destinationAirport, 
                  LocalDateTime departureTime, LocalDateTime arrivalTime, int durationMinutes, 
                  double price, int stops, Map<TravelClass, Integer> availableSeats) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.stops = stops;
        this.status = FlightStatus.SCHEDULED;
        this.availableSeats = availableSeats;
        
        // Defaults for display
        this.baggageAllowance = "15kg Cabin, 7kg Hand";
        this.cancellationPolicy = "Fully refundable 24h prior";
        this.amenities = "Wifi, Meals";
    }

    // Getters and Setters
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getAirline() { return airline; }
    public String getSourceAirport() { return sourceAirport; }
    public String getDestinationAirport() { return destinationAirport; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStops() { return stops; }
    public FlightStatus getStatus() { return status; }
    public void setStatus(FlightStatus status) { this.status = status; }
    public Map<TravelClass, Integer> getAvailableSeats() { return availableSeats; }
    public String getBaggageAllowance() { return baggageAllowance; }
    public String getCancellationPolicy() { return cancellationPolicy; }
    public String getAmenities() { return amenities; }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", airline='" + airline + '\'' +
                ", " + sourceAirport + " -> " + destinationAirport +
                ", dep=" + departureTime + ", arr=" + arrivalTime +
                ", duration=" + durationMinutes + "m" +
                ", price=" + price +
                ", stops=" + stops +
                '}';
    }
}
