package org.POS.backend.user;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponseDto(
        String name,
        String employeeId,
        String contactNumber,
        UserStatus status,
        String username,
        UserRole role,
        String email
) {
}
