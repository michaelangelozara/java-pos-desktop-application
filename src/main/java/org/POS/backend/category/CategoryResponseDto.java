package org.POS.backend.category;

public record CategoryResponseDto(
        String name,
        CategoryStatus status,
        String note
) {
}
