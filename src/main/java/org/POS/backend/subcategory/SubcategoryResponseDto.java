package org.POS.backend.subcategory;

public record SubcategoryResponseDto(
        int categoryId,
        String name,
        SubcategoryStatus status,
        String note
) {
}
