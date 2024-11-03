package org.POS.backend.product_subcategory;

public record ProductSubcategoryResponseDto(
        int id,
        String name,
        ProductSubcategoryStatus status,
        String categoryName,
        String code
) {
}
