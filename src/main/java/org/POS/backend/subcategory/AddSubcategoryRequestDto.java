package org.POS.backend.subcategory;

public record AddSubcategoryRequestDto(
        int categoryId,
        String name,
        SubcategoryStatus status,
        String note
) {
}
