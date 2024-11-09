package org.POS.backend.sale;

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
    String chequeNumber,
    String poReference,
    String paymentTerm,
    String deliveryPlace,
    String note
) {
}
