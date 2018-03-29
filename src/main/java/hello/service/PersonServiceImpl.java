package hello.service;


import hello.model.Person;
import hello.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("personService")
public class PersonServiceImpl implements PersonService {

  @Autowired
  PersonRepository personRepository;

  @Override
  public Person save(String name, String country) {
    Person n = new Person();
    n.setCountry(country);
    n.setName(name);
    personRepository.save(n); // At this moment an Id should be provided by auto-generation.
    return n; // Person with all fields populated
  }

  @Override
  public Person findById(Integer id) {
    List<Person> dbStatusPersons = personRepository.findAll();
    for (Person p : dbStatusPersons) {
      if (p.getId().equals(id)) {
        return p;
      }
    }
    return null;
  }

  @Override
  public Person update(Integer oldId, Person newPerson) {
    Person localPerson = findById(oldId); // localPerson owns the ID of the person found in DB
    localPerson.setName(newPerson.getName() + " (*)");
    localPerson.setCountry(newPerson.getCountry() + " (*)");
    newPerson = personRepository.save(localPerson);
    //since id matches another one within the DB, overwrite data!
    return newPerson;
  }

  @Override
  public void delete(Person b) {
    personRepository.delete(b);
  }

  @Override
  public List<Person> findAllUsers() {
    return personRepository.findAll();
  }

  @Override
  public void deleteAllUsers() {
    personRepository.deleteAll();
  }

  @Override
  public boolean userExists(Person c) {
    return findPerson(c) != null;
  }

  @Override
  public Person findPerson(Person n) {
    List<Person> dbStatusPersons = personRepository.findAll();
    for (Person p : dbStatusPersons) {
      if (p.getName().equalsIgnoreCase(n.getName()) &&
          p.getCountry().equalsIgnoreCase(n.getCountry())) {
        return p;
      }
    }
    return null;
  }
}
