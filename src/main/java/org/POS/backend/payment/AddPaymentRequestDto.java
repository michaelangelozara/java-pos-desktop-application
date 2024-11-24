package org.POS.backend.payment;

import java.math.BigDecimal;

public record AddPaymentRequestDto(
        PaymentDiscountType discountType,
        double discountAmount,
        TransactionType transactionType,
        BigDecimal paidAmount,
        String referenceNumber
) {
}
