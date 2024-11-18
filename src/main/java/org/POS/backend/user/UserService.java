package org.POS.backend.user;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user_log.UserLog;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

public class UserService {

    private UserDAO userDAO;

    private UserMapper userMapper;

    public UserService() {
        this.userDAO = new UserDAO();
        this.userMapper = new UserMapper();
    }

    public void add(AddUserRequestDto dto) {
        var fetchedUser = this.userDAO.getUserById(CurrentUser.id);
        if (fetchedUser == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var user = this.userMapper.toUser(dto);

        UserLog userLog = new UserLog();
        userLog.setCode(user.getEmployeeId());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.USER_MANAGEMENT_ADD_ACTION_LOG_PREFIX);
        fetchedUser.addUserLog(userLog);

        this.userDAO.add(user, userLog);
    }

    public void updateUser(UpdateUserRequestDto dto) {
        var admin = this.userDAO.getUserById(CurrentUser.id);
        if (admin == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var userToBeUpdated = this.userDAO.getUserById(dto.userId());

        if (userToBeUpdated != null) {
            var updatedUser = this.userMapper.toUpdatedUser(userToBeUpdated, dto);

            UserLog userLog = new UserLog();
            userLog.setCode(updatedUser.getEmployeeId());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.USER_MANAGEMENT_EDIT_ACTION_LOG_PREFIX);
            admin.addUserLog(userLog);
            this.userDAO.update(updatedUser, userLog);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void delete(int userId) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.USER_MANAGEMENT_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.userDAO.delete(userId, userLog);
    }

    public List<UserResponseDto> getAllValidUsers() {
        List<User> users = this.userDAO.getAllValidUsers();
        return this.userMapper.userResponseDtoList(users);
    }

    public UserResponseDto getValidUserById(int userId) {
        var user = this.userDAO.getUserById(userId);
        if (user != null)
            return this.userMapper.toUserResponseDto(user);

        return null;
    }

    public boolean authenticate(LoginRequestDto dto) {
        try {
            var user = this.userDAO.authenticateUserByUsernameAndPassword(dto.username());
            if (user == null)
                return false;

            String hashedPassword = user.getPassword();
            if (!BCrypt.checkpw(dto.password(), hashedPassword))
                return false;

            CurrentUser.id = user.getId();
            CurrentUser.employeeId = user.getEmployeeId();
            CurrentUser.username = user.getUsername();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserResponseDto> getAllValidUserByName(String name) {
        return this.userMapper.userResponseDtoList(this.userDAO.getAllValidUserByName(name));
    }
}
