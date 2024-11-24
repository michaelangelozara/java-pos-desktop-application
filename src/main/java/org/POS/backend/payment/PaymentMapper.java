package org.POS.backend.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentMapper {

    public Payment toPayment(AddPaymentRequestDto dto){
        Payment payment = new Payment();
        payment.setDiscountType(dto.discountType());
        payment.setTransactionType(dto.transactionType());
        payment.setPaidAmount(dto.paidAmount());
        payment.setReferenceNumber(dto.referenceNumber());
        payment.setDate(LocalDate.now());
        payment.setDiscount(BigDecimal.valueOf(dto.discountAmount()));
        return payment;
    }
}
