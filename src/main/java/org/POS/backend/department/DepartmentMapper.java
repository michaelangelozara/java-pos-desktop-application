package org.POS.backend.department;

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
}
