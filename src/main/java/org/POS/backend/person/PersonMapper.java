package org.POS.backend.person;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class PersonMapper {

    private CodeGeneratorService codeGeneratorService;

    public PersonMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

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
        person.setPersonCode(dto.personType().equals(PersonType.CLIENT) ? this.codeGeneratorService.generateProductCode(GlobalVariable.CLIENT_PREFIX) : this.codeGeneratorService.generateProductCode(GlobalVariable.SUPPLIER_PREFIX));
        return person;
    }

    public Person toUpdatedPerson(Person person, UpdatePersonRequestDto dto){
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
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getContactNumber(),
                person.getCompanyName(),
                person.getTaxRegistrationNumber(),
                person.getAddress(),
                person.getImage(),
                person.getStatus(),
                person.getPersonCode()
        );
    }

    public List<PersonResponseDto> personResponseDtoList(List<Person> people){
        return people
                .stream()
                .map(this::personResponseDto)
                .toList();
    }
}
