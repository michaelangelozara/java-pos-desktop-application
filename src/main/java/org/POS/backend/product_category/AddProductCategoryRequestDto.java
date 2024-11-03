package org.POS.backend.product_category;

public record AddProductCategoryRequestDto(
        String name,
        ProductCategoryStatus status,
        String note
) {
}
