package org.POS.backend.quoted_item;

import java.math.BigDecimal;

public record UpdateQuotedItemRequestDto(
        Integer quotedItemId,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        BigDecimal taxValue,
        BigDecimal subtotal,
        String productCode
) {
}
