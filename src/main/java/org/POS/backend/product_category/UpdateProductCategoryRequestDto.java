package org.POS.backend.product_category;

public record UpdateProductCategoryRequestDto(
        int productCategoryId,
        String name,
        ProductCategoryStatus status,
        String note
) {
}
