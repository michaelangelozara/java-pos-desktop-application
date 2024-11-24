package org.POS.backend.product;

import org.POS.backend.product_attribute.ProductAttribute;
import org.POS.backend.product_category.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProductResponseDto(
        int id,
        String name,
        String code,
        ProductUnit unit,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image,
        BigDecimal sellingPrice,
        BigDecimal purchasePrice,
        int stock,
        LocalDate date,
        ProductCategory category,
        List<ProductAttribute> productAttributes,
        ProductType type
) {
}
