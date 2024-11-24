package org.POS.backend.user_log;

import java.time.LocalDate;

public class UserLogMapper {

    public UserLog toUserLog(String action, String code) {
        UserLog userLog = new UserLog();
        userLog.setAction(action);
        userLog.setCode(code);
        userLog.setDate(LocalDate.now());

        return userLog;
    }
}
