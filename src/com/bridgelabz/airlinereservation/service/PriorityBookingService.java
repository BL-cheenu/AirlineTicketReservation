package com.bridgelabz.airlinereservation.service;

import com.bridgelabz.airlinereservation.model.Booking;
import com.bridgelabz.airlinereservation.model.Role;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityBookingService {
    // UC29: Express Booking Queue Management (PriorityQueue)
    // Priority: Admin > AirlineStaff > Passenger
    
    private PriorityQueue<Booking> bookingQueue;

    public PriorityBookingService() {
        this.bookingQueue = new PriorityQueue<>(new Comparator<Booking>() {
            @Override
            public int compare(Booking b1, Booking b2) {
                int p1 = getPriority(b1);
                int p2 = getPriority(b2);
                return Integer.compare(p1, p2);
            }
            
            private int getPriority(Booking b) {
                if (b.getBookedBy().getRole() == Role.ADMIN) return 1;
                if (b.getBookedBy().getRole() == Role.AIRLINE_STAFF) return 2;
                return 3;
            }
        });
    }

    public void addBookingToQueue(Booking booking) {
        bookingQueue.offer(booking);
        System.out.println("Booking added to express queue: " + booking.getPnr() + " (Priority: " + booking.getBookedBy().getRole() + ")");
    }

    public void processQueue() {
        System.out.println("--- Processing Express Booking Queue ---");
        while (!bookingQueue.isEmpty()) {
            Booking b = bookingQueue.poll();
            System.out.println("Processing Booking PNR: " + b.getPnr() + " for User Role: " + b.getBookedBy().getRole());
        }
    }
}
