package org.POS.backend.user;

public record UserResponseDto(
        int id,
        String name,
        String employeeId,
        String contactNumber,
        UserStatus status,
        String username,
        UserRole role,
        String email
) {
}
