package com.bridgelabz.airlinereservation.service.payment;

import com.bridgelabz.airlinereservation.exception.PaymentException;
import com.bridgelabz.airlinereservation.model.Booking;
import com.bridgelabz.airlinereservation.model.state.PaymentPendingState;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PaymentService {
    private Set<String> processedPayments = new HashSet<>();

    // UC15 & UC16: Process Payment with validation and security
    public String processBookingPayment(Booking booking, PaymentMethod paymentMethod, String discountCode) throws PaymentException {
        if (!(booking.getState() instanceof PaymentPendingState)) {
            throw new PaymentException("Booking is not in a payment pending state.");
        }

        // Prevent Duplicate Payments
        if (processedPayments.contains(booking.getPnr())) {
            throw new PaymentException("Payment already processed for this booking.");
        }

        // Calculate final amount with hypothetical discount
        double finalAmount = booking.getTotalFare();
        if ("SAVE10".equalsIgnoreCase(discountCode)) {
            finalAmount = finalAmount * 0.90;
            System.out.println("Applied 10% discount. New amount: Rs " + finalAmount);
        }

        // Security: Encrypting payment data (Simulation for PCI-DSS)
        String encryptedDetails = Base64.getEncoder().encodeToString(paymentMethod.getPaymentDetails().getBytes());
        System.out.println("Encrypted Payment payload transmitted securely: " + encryptedDetails);

        // Validation based on method type
        if (paymentMethod instanceof CardPayment) {
            String details = paymentMethod.getPaymentDetails();
            if (details.length() < 12) {
                throw new PaymentException("Invalid card details.");
            }
        } else if (paymentMethod instanceof UPIPayment) {
            if (!paymentMethod.getPaymentDetails().contains("@")) {
                throw new PaymentException("Invalid UPI ID.");
            }
        }

        // Process Transaction
        boolean success = paymentMethod.processPayment(finalAmount);
        
        if (success) {
            processedPayments.add(booking.getPnr());
            String transactionId = "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            System.out.println("Payment successful! Receipt generated. Transaction ID: " + transactionId);
            return transactionId;
        } else {
            throw new PaymentException("Payment failed at gateway.");
        }
    }

    // UC17: Refund Processing
    public String processRefund(Booking booking, boolean isFullRefund) throws PaymentException {
        if (!processedPayments.contains(booking.getPnr())) {
            throw new PaymentException("No completed payment found for this booking to refund.");
        }
        
        double refundAmount = booking.getTotalFare();
        if (!isFullRefund) {
            refundAmount = refundAmount * 0.50; // Partial refund logic
            System.out.println("Calculating partial refund amount: Rs " + refundAmount);
        } else {
            refundAmount = refundAmount - 500; // Flat cancellation charge
            System.out.println("Calculating full refund amount after Rs 500 cancellation charge: Rs " + refundAmount);
        }

        String refundTxnId = "REF" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        System.out.println("Refund processed successfully. Refund Transaction ID: " + refundTxnId);
        return refundTxnId;
    }
}
