package org.POS.backend.product_subcategory;

public record AddProductSubcategoryRequestDto(
        int categoryId,
        String name,
        ProductSubcategoryStatus status,
        String note
) {
}
