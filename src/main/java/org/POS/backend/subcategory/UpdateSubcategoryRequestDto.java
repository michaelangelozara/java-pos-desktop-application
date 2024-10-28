package org.POS.backend.subcategory;

import org.POS.backend.category.CategoryStatus;

public record UpdateSubcategoryRequestDto(
        int subcategoryId,
        String name,
        SubcategoryStatus status,
        String note,
        int categoryId
) {
}
