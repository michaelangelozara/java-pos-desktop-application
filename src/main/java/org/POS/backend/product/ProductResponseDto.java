package org.POS.backend.product;

import org.POS.backend.brand.BrandResponseDto;

import java.math.BigDecimal;

public record ProductResponseDto(
        String name,
        String model,
        String code,
        BrandResponseDto brand,
        ProductUnit unit,
        int tax,
        ProductTaxType taxType,
        BigDecimal regularPrice,
        int discount,
        String note,
        int alertQuantity,
        ProductStatus status,
        String image
) {
}
