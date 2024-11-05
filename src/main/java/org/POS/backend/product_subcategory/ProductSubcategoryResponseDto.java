package org.POS.backend.product_subcategory;

import org.POS.backend.product_category.ProductCategoryResponseDto;

public record ProductSubcategoryResponseDto(
        int id,
        String name,
        ProductSubcategoryStatus status,
        String categoryName,
        String code,
        ProductCategoryResponseDto productCategory,
        String note
) {
}
