package org.POS.backend.product;

import java.math.BigDecimal;

public record AddProductRequestDto(
        String name,
        String model,
        int brandId,
        ProductUnit unit,
        int productTax,
        ProductTaxType taxType,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        int percentageDiscount,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image,
        int stock
) {
}
