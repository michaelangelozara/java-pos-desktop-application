package org.POS.backend.person;

public record AddPersonRequestDto(
        String name,
        String email,
        String contactNumber,
        String companyName,
        String taxRegistration,
        String address,
        String image,
        PersonStatus status
) {
}
