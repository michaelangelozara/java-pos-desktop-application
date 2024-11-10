package org.POS.backend.purchased_item;

import java.math.BigDecimal;

public record AddPurchaseItemRequestDto(
        int productId,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        BigDecimal taxValue,
        BigDecimal subtotal
) {
}
