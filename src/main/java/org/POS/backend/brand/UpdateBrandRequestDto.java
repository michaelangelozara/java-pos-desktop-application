package org.POS.backend.brand;

public record UpdateBrandRequestDto(
        int brandId,
        String name,
        BrandStatus status,
        int subcategoryId
) {
}
