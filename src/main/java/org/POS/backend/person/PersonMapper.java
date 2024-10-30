package org.POS.backend.person;

import java.util.List;

public class PersonMapper {

    public Person toPerson(AddPersonRequestDto dto){
        Person person = new Person();
        person.setName(dto.name());
        person.setEmail(dto.email());
        person.setContactNumber(dto.contactNumber());
        person.setCompanyName(dto.companyName());
        person.setTaxRegistrationNumber(dto.taxRegistration());
        person.setType(dto.personType());
        person.setAddress(dto.address());
        person.setImage(dto.image());
        person.setStatus(dto.status());
        return person;
    }

    public Person toUpdatedPerson(UpdatePersonRequestDto dto){
        Person person = new Person();
        person.setId(dto.personId());
        person.setName(dto.name());
        person.setEmail(dto.email());
        person.setContactNumber(dto.contactNumber());
        person.setCompanyName(dto.companyName());
        person.setTaxRegistrationNumber(dto.taxRegistration());
        person.setType(dto.personType());
        person.setAddress(dto.address());
        person.setImage(dto.image());
        person.setStatus(dto.status());
        return person;
    }

    public PersonResponseDto personResponseDto(Person person){
        return new PersonResponseDto(
                person.getName(),
                person.getEmail(),
                person.getContactNumber(),
                person.getCompanyName(),
                person.getTaxRegistrationNumber(),
                person.getAddress(),
                person.getImage(),
                person.getStatus()
        );
    }

    public List<PersonResponseDto> personResponseDtoList(List<Person> people){
        return people
                .stream()
                .map(this::personResponseDto)
                .toList();
    }
}
