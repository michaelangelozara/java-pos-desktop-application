package org.POS.backend.purchased_product;

import java.math.BigDecimal;

public record UpdatePurchaseProductRequestDto(
        Integer purchaseProductId,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        BigDecimal taxValue,
        BigDecimal subtotal,
        String productCode
) {
}
