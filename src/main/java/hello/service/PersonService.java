package hello.service;

import hello.model.Person;

import java.util.List;

public interface PersonService {
  Person findPerson(Person n);
  Person findById(Integer id);
  Person save(String name, String country);
  Person update(Integer oldId, Person a);
  List<Person> findAllUsers();
  void delete(Person b);
  void deleteAllUsers();
  boolean userExists(Person c);

}
