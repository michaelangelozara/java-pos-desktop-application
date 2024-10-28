package org.POS.backend.department;

public record UpdateDepartmentRequestDto(
        int departmentId,
        String name,
        DepartmentStatus status,
        String note
) {
}
