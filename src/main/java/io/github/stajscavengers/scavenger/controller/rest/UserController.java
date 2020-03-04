package io.github.stajscavengers.scavenger.controller.rest;

import io.github.stajscavengers.scavenger.model.entity.Clue;
import io.github.stajscavengers.scavenger.model.entity.User;
import io.github.stajscavengers.scavenger.service.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  private final UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> post(@RequestBody User user) {
    userRepository.save(user);
    return ResponseEntity.created(user.getHref()).body(user);
  }


//  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//  public Iterable<User> get() {
//    return userRepository.getAllByOrderBy();
//  }

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> search(@RequestParam("q") String fragment) {
    return userRepository.getAllByUserNameContainsOrderByUserName(fragment);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable UUID id) {
    return userRepository.findOrFail(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> getList() {
    return userRepository.getAllByOrderByUserName();
  }

//  @PutMapping(value = "/{id}",
//      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//  public User put(@PathVariable UUID id, @RequestBody User modifiedUser) {
//    User user = userRepository.findOrFail(id);
//    user.setUserName(modifiedUser.getUserName());
//    return userRepository.save(user);
//  }

  @PutMapping(value = "/{userId}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public User edit(@PathVariable UUID userId, @RequestBody User updated) {
    User user = userRepository.findOrFail(userId);
    if (updated.getUserName() != null && !updated.getUserName().equals(user.getUserName())) {
      user.setUserName(updated.getUserName());
      userRepository.save(user);
    }
//    if (updated.getoAuthToken() != null && !updated.getoAuthToken().equals(user.getoAuthToken())) {
//      user.setoAuthToken(updated.getoAuthToken());
//      userRepository.save(user);
//    }
    return user;
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    userRepository.findById(id).ifPresent(userRepository::delete);


  }
}
