package org.POS.backend.person;

public record UpdatePersonRequestDto(
        int personId,
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
