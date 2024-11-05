package org.POS.backend.product;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        int productId,
        String name,
        String model,
        int brandId,
        ProductUnit unit,
        int tax,
        ProductTaxType taxType,
        BigDecimal regularPrice,
        int discount,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image,
        BigDecimal purchasePrice,
        int stock
) {
}
