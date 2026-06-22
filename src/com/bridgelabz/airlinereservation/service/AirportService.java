package com.bridgelabz.airlinereservation.service;

import com.bridgelabz.airlinereservation.model.Airport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AirportService {
    private List<Airport> airports = new ArrayList<>();

    // UC27: Airport Information Management
    public void addAirport(Airport airport) {
        if (airports.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(airport.getCode()))) {
            throw new IllegalArgumentException("Airport code already exists: " + airport.getCode());
        }
        airports.add(airport);
        System.out.println("Airport added: " + airport.getCode());
    }

    public void updateAirport(String code, Airport updatedAirport) {
        Airport airport = airports.stream()
                .filter(a -> a.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Airport not found: " + code));
        
        airport.setName(updatedAirport.getName());
        airport.setCity(updatedAirport.getCity());
        airport.setCountry(updatedAirport.getCountry());
        System.out.println("Airport updated: " + code);
    }

    public void removeAirport(String code) {
        boolean removed = airports.removeIf(a -> a.getCode().equalsIgnoreCase(code));
        if (removed) {
            System.out.println("Airport removed: " + code);
        } else {
            System.out.println("Airport not found: " + code);
        }
    }

    // UC28: Airport Search and Retrieval
    public List<Airport> searchAirport(String query) {
        return airports.stream()
                .filter(a -> a.getCode().toLowerCase().contains(query.toLowerCase()) || 
                             a.getName().toLowerCase().contains(query.toLowerCase()) ||
                             a.getCity().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
