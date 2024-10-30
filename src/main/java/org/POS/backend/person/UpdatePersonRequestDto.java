package org.POS.backend.person;

public record UpdatePersonRequestDto(
        int personId,
        String name,
        String email,
        String contactNumber,
        String companyName,
        String taxRegistration,
        PersonType personType,
        String address,
        String image,
        PersonStatus status
) {
}
