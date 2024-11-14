package org.POS.backend.quoted_item;

import java.math.BigDecimal;

public record AddQuotedItemRequestDto(
        int productId,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        BigDecimal taxValue,
        BigDecimal subtotal
) {
}
