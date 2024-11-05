package org.POS.backend.brand;

import org.POS.backend.product_subcategory.ProductSubcategoryResponseDto;

public record BrandResponseDto(
        int id,
        String name,
        BrandStatus status,
        ProductSubcategoryResponseDto productSubcategory
) {
}
