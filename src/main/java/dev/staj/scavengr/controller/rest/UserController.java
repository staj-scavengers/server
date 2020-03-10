package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.User;
import dev.staj.scavengr.service.UserRepository;
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

  /**
   * This method creates a new User entity.
   * @param user contains user fields.
   * @return Href to new user.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<User> post(@RequestBody User user) {
    userRepository.save(user);
    return ResponseEntity.created(user.getHref()).body(user);
  }

  /**
   * This method searches all users based on a string.
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

  /**
   * This method provides an option to change a user's name.  Unsure if we will use this or rely on
   * Google oAuth data.  Incorporates an "if not null" statement to handle empty requests.
   *
   * @param userId specifies an existing user.
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