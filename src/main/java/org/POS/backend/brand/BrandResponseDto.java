package org.POS.backend.brand;

import org.POS.backend.product_category.ProductCategory;
import org.POS.backend.product_subcategory.ProductSubcategory;

public record BrandResponseDto(
        int id,
        String name,
        BrandStatus status,
        ProductSubcategory productSubcategory,
        ProductCategory categoryResponseDto,
        String code
) {
}
