package org.POS.backend.purchased_product;

import java.math.BigDecimal;

public record AddPurchaseProductRequestDto(
        int productId,
        int quantity,
        BigDecimal purchasePrice
) {
}
