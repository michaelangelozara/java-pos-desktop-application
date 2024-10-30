package org.POS.backend.user;

public record LoginRequestDto(
        String username,
        String password
) {
}
