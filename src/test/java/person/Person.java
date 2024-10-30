package person;

import org.POS.backend.person.*;
import org.junit.jupiter.api.Test;

public class Person {

    @Test
    void add(){
        PersonService personService = new PersonService();
        AddPersonRequestDto dto = new AddPersonRequestDto(
                "Kotlin Steve",
                "Steve@gmail.com",
                "09090909090",
                "NedNets Inc.",
                "KIGG-xZIZ-112",
                PersonType.CLIENT,
                "New York",
                "JSISSHJLO267<L",
                PersonStatus.ACTIVE
        );

        personService.add(dto);
    }

    @Test
    void update(){
        PersonService personService = new PersonService();
        UpdatePersonRequestDto dto = new UpdatePersonRequestDto(
                1,
                "James Smith",
                "smith@gmail.com",
                "09090909090",
                "ICTSO Inc.",
                "KKA-ZUIZ-1",
                PersonType.SUPPLIER,
                "Los Angeles Chicago",
                "HBJNKM<L",
                PersonStatus.ACTIVE
        );

        personService.update(dto);
    }

    @Test
    void delete(){
        PersonService personService = new PersonService();
        personService.delete(1);
    }

    @Test
    void getAllValidPeople(){
        PersonService personService = new PersonService();
        personService.getAllValidPeople().forEach(p -> {
            System.out.println(p.name());
        });
    }

    @Test
    void getValidPerson(){
        PersonService personService = new PersonService();
        System.out.println(personService.getValidPersonById(1));
    }
}
