package org.POS.backend.subcategory;

public record UpdateSubcategoryRequestDto(
        int subcategoryId,
        String name,
        SubcategoryStatus status,
        String note,
        String code,
        int categoryId
) {
}
