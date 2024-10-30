package org.POS.backend.person;

public record AddPersonRequestDto(
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
