package user;

import org.POS.backend.exception.ResourceNotFoundException;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.user.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class User {


    @Test
    void add(){
        UserService userService = new UserService();

        AddUserRequestDto dto = new AddUserRequestDto(
                "Michael Angelo Zara",
                "Ewan",
                "YUNJKADS",
                "09090909099",
                BigDecimal.valueOf(1000),
                0,
                LocalDate.now(),
                UserGender.MALE,
                "A+",
                UserReligion.CHRISTIANS,
                LocalDate.now(),
                LocalDate.now(),
                "Pimbalayan, Lambayong, Sultan Kudarat",
                UserStatus.ACTIVE,
                "YVHIYIYFGVBJIJUYVHBUBJ",
                true,
                "username",
                "password",
                UserRole.SALESMAN,
                1
        );
        userService.add(dto);
    }

    @Test
    void update(){
        UserService userService = new UserService();

        UpdateUserRequestDto dto = new UpdateUserRequestDto(
                1,
                "Michael Angelo B. Zara",
                "Ewan",
                "YUNJKADS",
                "09090909099",
                BigDecimal.valueOf(1000),
                0,
                LocalDate.now(),
                UserGender.MALE,
                "A+",
                UserReligion.CHRISTIANS,
                LocalDate.now(),
                LocalDate.now(),
                "Pimbalayan, Lambayong, Sultan Kudarat",
                UserStatus.ACTIVE,
                "YVHIYIYFGVBJIJUYVHBUBJ",
                true,
                "username",
                "password",
                UserRole.SALESMAN,
                1
        );
        userService.updateUser(dto);
    }

    @Test
    void delete(){
        UserService userService = new UserService();

        int userId = 1;

        userService.delete(userId);
    }

    @Test
    void getAllValidUsers() throws ResourceNotFoundException {
        UserService userService = new UserService();

        System.out.println(userService.getAllValidUsers());
    }

    @Test
    void getValidUser() throws ResourceNotFoundException {
        UserService userService = new UserService();

        System.out.println(userService.getValidUserById(1));
    }

    @Test
    void authenticate(){
        LoginRequestDto dto = new LoginRequestDto("username", "password");

        UserService userService = new UserService();
        System.out.println(userService.authenticate(dto));
        System.out.println("USER INFORMATION");
        System.out.println(CurrentUser.id);
        System.out.println(CurrentUser.employeeId);
        System.out.println(CurrentUser.username);
    }
}
