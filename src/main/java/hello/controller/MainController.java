package hello.controller;

import hello.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.service.PersonService;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class MainController {

  @Autowired
  PersonService personService;

  @PostMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
  public ResponseEntity<String> addNewUser(@RequestBody Person person) {

    if (personService.userExists(person)) {
      return new ResponseEntity<> ("User already there!", HttpStatus.CONFLICT);
    }

    Person personAdded = personService.save(person.getName(), person.getCountry());
    return new ResponseEntity<>(personAdded.toString() + " Created successfully", HttpStatus.CREATED);
  }

  @GetMapping(value = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  // Map ONLY GET Requests
  public ResponseEntity<Person> getUser(@PathVariable("id") Integer id) {
    // This returns a JSON or XML with the users
    Person aPerson = personService.findById(id);
    if (aPerson == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(aPerson, HttpStatus.OK);
  }

  @PutMapping(path = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  // Map ONLY POST Requests
  public ResponseEntity<String> updateUser(@PathVariable("id") Integer id,
      @RequestBody Person person) {

    Person personToFetch = personService.findById(id);

    if (personToFetch == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Person temp = personService.update(id, person);
    return new ResponseEntity<>(temp.toString() + " Person " + id + " updated!", HttpStatus.OK);
  }

  @DeleteMapping(value = "user/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
    Person personToFetch = personService.findById(id);
    if (personToFetch == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    personService.delete(personToFetch);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "user")
  public ResponseEntity<Void> deleteAllUsers() {
    List<Person> personToFetch = personService.findAllUsers();
    if (personToFetch.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.ACCEPTED);// No changes
    }

    personService.deleteAllUsers();
    return new ResponseEntity<>(HttpStatus.OK);
  }


  @GetMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY GET Requests
  public ResponseEntity<List<Person>> getAllUsers() {
    List<Person> users = personService.findAllUsers();
    if (users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Empty body
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
  }
}
