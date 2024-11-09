package org.POS.backend.user;

public record AddUserRequestDto(
        String userId,
        String name,
        UserRole role,
        String username,
        String password,
        String email,
        String contactNumber,
        UserStatus status
) {
}
