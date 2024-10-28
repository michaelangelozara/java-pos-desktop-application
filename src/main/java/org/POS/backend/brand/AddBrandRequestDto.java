package org.POS.backend.brand;

public record AddBrandRequestDto(
        String name,
        BrandStatus status,
        int subcategoryId
) {
}
