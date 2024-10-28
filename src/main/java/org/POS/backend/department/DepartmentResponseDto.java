package org.POS.backend.department;

public record DepartmentResponseDto(
        int departmentId,
        String name,
        DepartmentStatus status,
        String note
) {
}
