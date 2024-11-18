package org.POS.backend.user;

public record UpdateUserRequestDto(
        int userId,
        String name,
        UserRole role,
        String password,
        String email,
        String contactNumber,
        UserStatus status
) {
}