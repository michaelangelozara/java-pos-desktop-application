package org.POS.backend.person;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;

public class PersonService {

    private PersonDAO personDAO;

    private PersonMapper personMapper;

    private UserDAO userDAO;

    public PersonService(){
        this.personDAO = new PersonDAO();
        this.personMapper = new PersonMapper();
        this.userDAO = new UserDAO();
    }

    public String add(AddPersonRequestDto dto){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var person = this.personMapper.toPerson(dto);

        UserLog userLog = new UserLog();
        userLog.setCode(person.getPersonCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(person.getType().equals(PersonType.CLIENT) ? UserActionPrefixes.CLIENTS_ADD_ACTION_LOG_PREFIX : UserActionPrefixes.SUPPLIERS_ADD_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.personDAO.add(person, userLog);;
        return GlobalVariable.PERSON_ADDED;
    }

    public String update(UpdatePersonRequestDto dto){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var person = this.personDAO.getValidPersonById(dto.personId());

        if(person == null)
            return GlobalVariable.PERSON_NOT_FOUND;

        var updatedPerson = this.personMapper.toUpdatedPerson(person, dto);

        UserLog userLog = new UserLog();
        userLog.setCode(updatedPerson.getPersonCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(person.getType().equals(PersonType.CLIENT) ? UserActionPrefixes.CLIENTS_EDIT_ACTION_LOG_PREFIX : UserActionPrefixes.SUPPLIERS_EDIT_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.personDAO.update(updatedPerson, userLog);
        return GlobalVariable.PERSON_UPDATED;
    }

    public String delete(int personId){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        user.addUserLog(userLog);

        boolean result = this.personDAO.delete(personId, userLog);
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

    public List<PersonResponseDto> getAllValidPeopleByType(PersonType type){
        var people = this.personDAO.getAllValidPeopleByType(type);
        return this.personMapper.personResponseDtoList(people);
    }

    public List<PersonResponseDto> getAllValidPersonByName(String name, PersonType type){
        return this.personMapper.personResponseDtoList(this.personDAO.getAllValidPersonByName(name, type));
    }

}
