package org.POS.backend.product;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        int productId,
        int categoryId,
        String name,
        ProductUnit unit,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        int stock
) {
}
