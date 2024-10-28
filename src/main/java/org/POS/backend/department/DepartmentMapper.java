package org.POS.backend.department;

import java.util.List;

public class DepartmentMapper {

    public Department toDepartment(AddDepartmentRequestDto dto){
        Department department = new Department();
        department.setName(dto.name());
        department.setStatus(dto.status());
        department.setNote(dto.note());
        return department;
    }

    public Department toUpdatedDepartment(UpdateDepartmentRequestDto dto){
        Department department = new Department();
        department.setId(dto.departmentId());
        department.setName(dto.name());
        department.setStatus(dto.status());
        department.setNote(dto.note());
        return department;
    }

    public DepartmentResponseDto toDepartmentResponseDto(Department department){
        return new DepartmentResponseDto(
                department.getId(),
                department.getName(),
                department.getStatus(),
                department.getNote()
        );
    }

    public List<DepartmentResponseDto> departmentResponseDtoList(List<Department> departments){
        return departments
                .stream()
                .map(this::toDepartmentResponseDto)
                .toList();
    }
}
