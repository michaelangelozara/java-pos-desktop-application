package org.POS.backend.product;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        int productId,
        String name,
        String model,
        String code,
        int brandId,
        ProductUnit unit,
        int tax,
        ProductTaxType taxType,
        BigDecimal regularPrice,
        int discount,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image
) {
}
