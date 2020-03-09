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

  /**
   * @param fragment is a search string entered in the url.
   * @return list of users with the requested string in their name.
   */
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> search(@RequestParam("q") String fragment) {
    return userRepository.getAllByUserNameContains(fragment);
  }

  /**
   * @param id is the user's unique id
   * @return an individual user
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable UUID id) {
    return userRepository.findOrFail(id);
  }

  /**
   * @return all users.  We may not need this method.
   */
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

  /**
   * This method provides an option to change a user's name.  Unsure if we will use this or rely on
   * Google oAuth data.
   *
   * @param userId  gets an existing user.
   * @param updated brings a new user name from the JSON request.
   * @return the same user with an updated UserName field.
   */
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

  /**
   * Deletes a single user.
   * @param id is the user to be deleted.
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    userRepository.findById(id).ifPresent(userRepository::delete);


  }
}
