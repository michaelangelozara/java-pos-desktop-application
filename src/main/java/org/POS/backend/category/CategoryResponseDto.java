package org.POS.backend.category;

public record CategoryResponseDto(
        int id,
        String name,
        CategoryStatus status,
        String note
) {
}
