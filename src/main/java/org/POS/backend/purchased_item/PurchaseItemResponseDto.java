package org.POS.backend.purchased_item;

import org.POS.backend.product.ProductResponseDto;

import java.math.BigDecimal;

public record PurchaseItemResponseDto(
        int id,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        BigDecimal taxValue,
        BigDecimal subtotal,
        ProductResponseDto product
) {
}
