package org.POS.backend.user;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateUserRequestDto(
        int userId,
        String name,
        String designation,
        String employeeId,
        String contactNumber,
        BigDecimal salary,
        int commission,
        LocalDate birthDate,
        UserGender gender,
        String bloodGroup,
        UserReligion religion,
        LocalDate appointmentDate,
        LocalDate joinDate,
        String address,
        UserStatus status,
        String profilePicture,
        boolean isAccountAllowed,
        String username,
        String password,
        UserRole role,
        int departmentId
) {
}
