package com.bridgelabz.airlinereservation.service;

import com.bridgelabz.airlinereservation.model.Booking;
import com.bridgelabz.airlinereservation.model.PassengerProfile;
import com.bridgelabz.airlinereservation.service.payment.PaymentService;

import java.util.Iterator;

public class CancellationService {
    private PaymentService paymentService;

    public CancellationService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // UC21: Full Booking Cancellation
    public void cancelFullBooking(Booking booking) {
        System.out.println("Initiating FULL cancellation for PNR: " + booking.getPnr());
        try {
            String refundTxn = paymentService.processRefund(booking, true);
            System.out.println("Booking fully cancelled. Refund Processed: " + refundTxn);
            // In a real app we'd transition to CancelledState
            System.out.println("State transition to CANCELLED.");
        } catch (Exception e) {
            System.err.println("Cancellation failed: " + e.getMessage());
        }
    }

    // UC22: Partial Booking Cancellation
    public void cancelPartialBooking(Booking booking, String passengerId) {
        System.out.println("Initiating PARTIAL cancellation for PNR: " + booking.getPnr() + " for Passenger ID: " + passengerId);
        
        boolean found = false;
        Iterator<PassengerProfile> iterator = booking.getPassengers().iterator();
        while (iterator.hasNext()) {
            PassengerProfile p = iterator.next();
            if (p.getPassportOrId().equals(passengerId)) {
                iterator.remove();
                found = true;
                break;
            }
        }
        
        if (found) {
            try {
                // Partial refund process
                String refundTxn = paymentService.processRefund(booking, false);
                System.out.println("Partial cancellation successful. Refund Processed: " + refundTxn);
                booking.calculateTotalFare(); // Recalculate fare for remaining pax
            } catch (Exception e) {
                System.err.println("Partial Cancellation failed: " + e.getMessage());
            }
        } else {
            System.out.println("Passenger not found in booking.");
        }
    }

    // UC23: Cancellation Policy Management
    public void displayCancellationPolicy() {
        System.out.println("--- Cancellation Policy ---");
        System.out.println("1. Full Cancellation > 24 hours before departure: Flat Rs 500 charge.");
        System.out.println("2. Full Cancellation < 24 hours before departure: 50% charge.");
        System.out.println("3. Partial Cancellation: 50% refund for the cancelled passenger's fare.");
    }
}
