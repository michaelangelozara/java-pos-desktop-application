package org.POS.backend.user;

import org.POS.backend.department.DepartmentDAO;
import org.POS.backend.department.DepartmentService;
import org.POS.backend.exception.ResourceNotFoundException;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class UserService {

    private UserDAO userDAO;

    private DepartmentService departmentService;

    private DepartmentDAO departmentDAO;

    private UserMapper userMapper;

    public UserService(){
        this.userDAO = new UserDAO();
        this.userMapper = new UserMapper();
        this.departmentService = new DepartmentService();
        this.departmentDAO = new DepartmentDAO();
    }

    public void add(AddUserRequestDto dto){
        var department = this.departmentDAO.getDepartmentById(dto.departmentId());
        var user = this.userMapper.toUser(dto, department);
        this.userDAO.add(user);
    }

    public void updateUser(UpdateUserRequestDto dto){
        var department = this.departmentDAO.getDepartmentById(dto.departmentId());
        var updatedUser = this.userMapper.toUpdatedUser(dto, department);
        this.userDAO.update(updatedUser);
    }

    public void delete(int userId){
        this.userDAO.delete(userId);
    }

    public List<UserResponseDto> getAllValidUsers() throws ResourceNotFoundException {
        List<User> users = this.userDAO.getAllValidUsers();
        return this.userMapper.userResponseDtoList(users);
    }

    public UserResponseDto getValidUserById(int userId){
        var user = this.userDAO.getUserById(userId);
        if(user != null)
            return this.userMapper.toUserResponseDto(user);

        return null;
    }

    public String authenticate(LoginRequestDto dto){
        var user = this.userDAO.authenticateUserByUsernameAndPassword(dto.username(), dto.password());
        if(user == null)
            return GlobalVariable.USER_INVALID_CREDENTIAL;

        CurrentUser.id = user.getId();
        CurrentUser.employeeId = user.getEmployeeId();
        CurrentUser.username = user.getUsername();

        return GlobalVariable.USER_LOGGED_IN;
    }
}
