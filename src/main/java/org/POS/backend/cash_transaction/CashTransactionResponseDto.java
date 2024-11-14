package org.POS.backend.cash_transaction;

import java.math.BigDecimal;

public record CashTransactionResponseDto(
        int id,
        String reference,
        BigDecimal cashIn,
        BigDecimal cashOut,
        TransactionPaymentMethod paymentMethod,
        String username,
        String dateTime
) {
}
