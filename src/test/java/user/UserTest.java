package user;

import org.POS.backend.user.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserTest {


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
}
