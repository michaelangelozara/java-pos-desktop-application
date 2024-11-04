package org.POS.backend.person;

public record PersonResponseDto(
        int id,
        String name,
        String email,
        String contactNumber,
        String companyName,
        String taxRegistration,
        String address,
        String image,
        PersonStatus status,
        String code
) {
}
