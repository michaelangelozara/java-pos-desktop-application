package org.POS.backend.product_subcategory;

public record UpdateProductSubcategoryRequestDto(
        int subcategoryId,
        String name,
        ProductSubcategoryStatus status,
        String note,
        String code,
        int categoryId
) {
}
