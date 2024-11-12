package org.POS.backend.sale;

import org.POS.backend.cash_transaction.CashTransactionPaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddSaleRequestDto(
    int customerId,
    String discountType,
    BigDecimal discount,
    BigDecimal transportCost,
    BigDecimal totalTax,
    BigDecimal netTotal,
    String receiptNumber,
    BigDecimal amount,
    LocalDate date,
    String poReference,
    String deliveryPlace,
    String note,
    CashTransactionPaymentMethod paymentMethod,
    BigDecimal change
) {
}
