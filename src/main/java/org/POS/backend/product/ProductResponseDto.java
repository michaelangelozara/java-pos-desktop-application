package org.POS.backend.product;

import org.POS.backend.brand.BrandResponseDto;
import org.POS.backend.product_subcategory.ProductSubcategoryResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponseDto(
        int id,
        String name,
        String model,
        String code,
        BrandResponseDto brand,
        ProductUnit unit,
        int tax,
        ProductTaxType taxType,
        int discount,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image,
        BigDecimal sellingPrice,
        BigDecimal purchasePrice,
        int stock,
        LocalDate date
) {
}
