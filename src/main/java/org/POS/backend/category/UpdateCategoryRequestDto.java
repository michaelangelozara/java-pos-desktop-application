package org.POS.backend.category;

public record UpdateCategoryRequestDto(
        int categoryId,
        String name,
        CategoryStatus status,
        String note
) {
}
