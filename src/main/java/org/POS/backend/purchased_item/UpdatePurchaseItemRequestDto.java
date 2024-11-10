package org.POS.backend.purchased_item;

import java.math.BigDecimal;

public record UpdatePurchaseItemRequestDto(
        Integer purchaseProductId,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        BigDecimal taxValue,
        BigDecimal subtotal,
        String productCode
) {
}
