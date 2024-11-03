package org.POS.backend.product_category;

public record UpdateProductCategoryRequestDto(
        int categoryId,
        String name,
        ProductCategoryStatus status,
        String note
) {
}
