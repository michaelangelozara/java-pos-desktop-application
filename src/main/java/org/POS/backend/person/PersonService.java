package org.POS.backend.person;

import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class PersonService {

    private PersonDAO personDAO;

    private PersonMapper personMapper;

    public PersonService(){
        this.personDAO = new PersonDAO();
        this.personMapper = new PersonMapper();
    }

    public String add(AddPersonRequestDto dto){
        var person = this.personMapper.toPerson(dto);
        this.personDAO.add(person);;
        return GlobalVariable.PERSON_ADDED;
    }

    public String update(UpdatePersonRequestDto dto){
        var person = this.personDAO.getValidPersonById(dto.personId());

        if(person == null)
            return GlobalVariable.PERSON_NOT_FOUND;

        var updatedPerson = this.personMapper.toUpdatedPerson(dto);
        this.personDAO.update(updatedPerson);
        return GlobalVariable.PERSON_UPDATED;
    }

    public String delete(int personId){
        boolean result = this.personDAO.delete(personId);
        if(result)
            return GlobalVariable.PERSON_DELETED;

        return GlobalVariable.PERSON_NOT_FOUND;
    }

    public PersonResponseDto getValidPersonById(int personId){
        var person = this.personDAO.getValidPersonById(personId);

        if(person == null)
            return null;

        return this.personMapper.personResponseDto(person);
    }

    public List<PersonResponseDto> getAllValidPeople(){
        var people = this.personDAO.getAllValidPeople();
        return this.personMapper.personResponseDtoList(people);
    }
}
