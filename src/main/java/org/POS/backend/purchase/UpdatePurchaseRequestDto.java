package org.POS.backend.purchase;

import org.POS.backend.purchased_item.UpdatePurchaseItemRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record UpdatePurchaseRequestDto(
        int purchaseId,
        List<UpdatePurchaseItemRequestDto> purchaseItems,
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
