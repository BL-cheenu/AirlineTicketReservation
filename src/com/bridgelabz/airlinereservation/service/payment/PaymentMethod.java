package com.bridgelabz.airlinereservation.service.payment;

public interface PaymentMethod {
    boolean processPayment(double amount);
    String getPaymentDetails();
}
