package org.POS.backend.category;

public record AddCategoryRequestDto(
        String name,
        CategoryStatus status,
        String note
) {
}
