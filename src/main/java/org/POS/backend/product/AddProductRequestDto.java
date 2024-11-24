package org.POS.backend.product;

import java.math.BigDecimal;

public record AddProductRequestDto(
        int categoryId,
        String name,
        int stock,
        ProductUnit unit,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image,
        ProductType type,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice
) {
}
