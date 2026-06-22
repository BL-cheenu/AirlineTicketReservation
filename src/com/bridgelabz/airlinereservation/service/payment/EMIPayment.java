package com.bridgelabz.airlinereservation.service.payment;

public class EMIPayment implements PaymentMethod {
    private String bankName;
    private int emiMonths;

    public EMIPayment(String bankName, int emiMonths) {
        this.bankName = bankName;
        this.emiMonths = emiMonths;
    }

    @Override
    public boolean processPayment(double amount) {
        double emiAmount = amount / emiMonths;
        System.out.println("Processing EMI Payment of Rs " + amount + " through " + bankName + ".");
        System.out.println("Monthly EMI: Rs " + String.format("%.2f", emiAmount) + " for " + emiMonths + " months.");
        // Simulate success
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return "EMI - " + bankName + " (" + emiMonths + " Months)";
    }
}
