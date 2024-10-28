package org.POS.backend.department;

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

    public Department getDepartmentById(int departmentId){
        return this.departmentDAO.getDepartmentById(departmentId);
    }


}
