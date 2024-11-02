package org.POS.backend.subcategory;

public record SubcategoryResponseDto(
        int id,
        String name,
        SubcategoryStatus status,
        String categoryName,
        String code
) {
}
