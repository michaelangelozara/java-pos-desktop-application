package org.POS.backend.department;

public record AddDepartmentRequestDto(
    String name,
    DepartmentStatus status,
    String note
) {
}
