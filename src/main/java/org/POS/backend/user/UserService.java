package org.POS.backend.user;

import org.POS.backend.department.DepartmentService;

public class UserService {

    private UserDAO userDAO;

    private DepartmentService departmentService;

    private UserMapper userMapper;

    public UserService(){
        this.userDAO = new UserDAO();
        this.userMapper = new UserMapper();
        this.departmentService = new DepartmentService();
    }

    public void add(AddUserRequestDto dto){
        var department = this.departmentService.getDepartmentById(dto.departmentId());
        var user = this.userMapper.toUser(dto, department);
        this.userDAO.add(user);
    }

//    public void
}
