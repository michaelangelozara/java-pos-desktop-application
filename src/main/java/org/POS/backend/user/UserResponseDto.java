package org.POS.backend.user;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponseDto(
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
        String username,
        UserRole role
) {
}
