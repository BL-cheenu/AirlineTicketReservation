package com.bridgelabz.airlinereservation.service.payment;

public class UPIPayment implements PaymentMethod {
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing UPI Payment of Rs " + amount + " for UPI ID: " + upiId);
        // Simulate success
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return "UPI - " + upiId;
    }
}
