package org.POS.backend.department;

import java.util.List;

public class DepartmentService {

    private DepartmentDAO departmentDAO;

    private DepartmentMapper departmentMapper;

    public DepartmentService() {
        this.departmentDAO = new DepartmentDAO();
        this.departmentMapper = new DepartmentMapper();
    }

    public void add(AddDepartmentRequestDto dto){
        var department = this.departmentMapper.toDepartment(dto);
        this.departmentDAO.add(department);
    }

    public void update(UpdateDepartmentRequestDto dto){
        var updatedDepartment = this.departmentMapper.toUpdatedDepartment(dto);
        this.departmentDAO.update(updatedDepartment);
    }

    public void delete(int departmentId){
        this.departmentDAO.delete(departmentId);
    }

    public DepartmentResponseDto getDepartmentById(int departmentId){
        var department = this.departmentDAO.getDepartmentById(departmentId);
        if(department != null)
            return this.departmentMapper.toDepartmentResponseDto(department);
        return null;
    }

    public List<DepartmentResponseDto> getAllValidDepartment(){
        List<Department> departments = this.departmentDAO.getAllValidDepartment();
        return this.departmentMapper.departmentResponseDtoList(departments);
    }

}
