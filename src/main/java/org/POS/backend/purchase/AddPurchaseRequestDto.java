package org.POS.backend.purchase;

import org.POS.backend.purchased_product.PurchaseProduct;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record AddPurchaseRequestDto(
        int supplierId,
        Set<PurchaseProduct> purchaseProducts,
        String purchaseOrderReference,
        String paymentTerm,
        int purchaseTax,
        String note,
        LocalDate purchaseDate,
        LocalDate purchaseOrderDate,
        PurchaseStatus status,
        BigDecimal discount,
        BigDecimal transportCost,
        int productQuantity,
        BigDecimal productPurchasePrice
) {
}
