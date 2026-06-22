package com.bridgelabz.airlinereservation.service.payment;

public class CardPayment implements PaymentMethod {
    private String cardNumber;
    private String cardHolderName;

    public CardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing Card Payment of Rs " + amount + " for Card: ****" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)));
        // Simulate success
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return "Card - ****" + cardNumber.substring(Math.max(0, cardNumber.length() - 4));
    }
}
