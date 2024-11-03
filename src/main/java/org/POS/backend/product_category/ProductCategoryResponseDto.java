package org.POS.backend.product_category;

public record ProductCategoryResponseDto(
        int id,
        String name,
        ProductCategoryStatus status,
        String note,
        String code
) {
}
