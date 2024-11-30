package org.POS.backend.global_variable;

import org.POS.backend.user.UserRole;

public class CurrentUser {

    public static int id = 0;

    public static String employeeId = "";

    public static String username = "";

    public static boolean isPosLoginSetup = false;

    public static UserRole role = null;
}
