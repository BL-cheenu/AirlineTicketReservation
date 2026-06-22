package com.bridgelabz.airlinereservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightSearchCriteria {
    private String source;
    private String destination;
    private LocalDate departureDate;
    private TravelClass travelClass;
    private int passengers;

    // Optional Filters
    private String preferredAirline;
    private Integer maxStops;
    private Double maxPrice;
    private LocalTime minDepartureTime;
    private LocalTime maxDepartureTime;

    public FlightSearchCriteria(String source, String destination, LocalDate departureDate) {
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.travelClass = TravelClass.ECONOMY;
        this.passengers = 1;
    }

    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public LocalDate getDepartureDate() { return departureDate; }
    public TravelClass getTravelClass() { return travelClass; }
    public void setTravelClass(TravelClass travelClass) { this.travelClass = travelClass; }
    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }

    public String getPreferredAirline() { return preferredAirline; }
    public void setPreferredAirline(String preferredAirline) { this.preferredAirline = preferredAirline; }
    public Integer getMaxStops() { return maxStops; }
    public void setMaxStops(Integer maxStops) { this.maxStops = maxStops; }
    public Double getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }
    public LocalTime getMinDepartureTime() { return minDepartureTime; }
    public void setMinDepartureTime(LocalTime minDepartureTime) { this.minDepartureTime = minDepartureTime; }
    public LocalTime getMaxDepartureTime() { return maxDepartureTime; }
    public void setMaxDepartureTime(LocalTime maxDepartureTime) { this.maxDepartureTime = maxDepartureTime; }
}
