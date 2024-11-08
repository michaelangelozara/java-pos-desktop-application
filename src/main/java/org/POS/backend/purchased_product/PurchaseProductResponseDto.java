package org.POS.backend.purchased_product;

import org.POS.backend.product.ProductResponseDto;

import java.math.BigDecimal;

public record PurchaseProductResponseDto(
        int id,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        BigDecimal taxValue,
        BigDecimal subtotal,
        ProductResponseDto product
) {
}
