package org.POS.backend.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddPurchaseRequestDto(
        int supplierId,
        String purchaseOrderReference,
        String paymentTerm,
        double purchaseTax,
        BigDecimal totalTax,
        BigDecimal subtotalTax,
        BigDecimal netSubtotal,
        BigDecimal discount,
        BigDecimal transportCost,
        String account,
        String chequeNumber,
        String receiptNumber,
        String note,
        LocalDate purchaseDate,
        LocalDate purchaseOrderDate,
        PurchaseStatus status,
        BigDecimal totalPaid
) {
}
