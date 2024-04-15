package library.services;

import library.models.Person;
import library.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> showAllPeople(){
        return personRepository.findAll();
    }

    public Person showOnePerson(int id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void createPerson(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person updatePerson){
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    @Transactional
    public void deletePerson(int id){
        personRepository.deleteById(id);
    }

    public List<Person> findPeopleByFilter(String filter){
        return personRepository.findByNameEqualsIgnoreCase(filter);
    }
}
