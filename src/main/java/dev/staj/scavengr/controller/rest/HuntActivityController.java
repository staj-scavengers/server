package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.HuntActivity;
import dev.staj.scavengr.model.entity.Organizer;
import dev.staj.scavengr.model.entity.User;
import dev.staj.scavengr.service.HuntActivityRepository;
import dev.staj.scavengr.service.HuntRepository;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller Class uses HTTP and JSON values to do {@link HuntActivity} CRUD Operations
 * through the {@link HuntActivityRepository}.
 *
 * @author STAJ
 */
@RestController
@RequestMapping("/hunt-activities")
@ExposesResourceFor(HuntActivity.class)
public class HuntActivityController {

  private final HuntActivityRepository huntActivityRepository;
  private final HuntRepository huntRepository;
  private final UserRepository userRepository;

  /**
   * The HuntActivityController constructor initializes the three repositories the controller needs
   * access to.
   *
   * @param huntActivityRepository contains methods for manipulating {@link HuntActivity} records.
   * @param huntRepository         contains methods for manipulating {@link Hunt}s.
   * @param userRepository         contains methods for manipulating {@link User}s
   */
  @Autowired
  public HuntActivityController(HuntActivityRepository huntActivityRepository,
      HuntRepository huntRepository, UserRepository userRepository) {
    this.huntActivityRepository = huntActivityRepository;
    this.huntRepository = huntRepository;
    this.userRepository = userRepository;
  }

  /**
   * This method creates a new {@link HuntActivity}.
   *
   * @param huntActivity contains HuntActivity fields.
   * @return Href address for the new HuntActivity.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<HuntActivity> post(@RequestBody HuntActivity huntActivity) {
    huntActivityRepository.save(huntActivity);
    return ResponseEntity.created(huntActivity.getHref()).body(huntActivity);
  }

  //FIXME with ScavengrService getHuntActivityByUser
  /**
   * This method returns all {@link HuntActivity} records for a single {@link User}, ordered by date
   * started.
   *
   * @param user is the User's records to search for.
   * @return an {@link Iterable}<{@link HuntActivity}> collection.
   */
  @GetMapping(value = "/search/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HuntActivity> getByUser(@PathVariable User user) {
    return huntActivityRepository.getAllByUserOrderByStarted(user);
  }

  //FIXME with ScavengrService getHuntActivityByHunt
  /**
   * This method returns all {@link HuntActivity} records for a single {@link Hunt}, ordered by date
   * {@link User}.
   *
   * @param hunt is the Hunt's records to search for.
   * @return an {@link Iterable}<{@link HuntActivity}> collection.
   */
  @GetMapping(value = "/{hunt}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HuntActivity> getByHunt(@PathVariable Hunt hunt) {
    return huntActivityRepository.getAllByHuntOrderByUser(hunt);
  }

  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public HuntActivity edit(@PathVariable UUID id, @RequestBody HuntActivity updated) {
    HuntActivity huntActivity = huntActivityRepository.findOrFail(id);
    if (updated.getCluesCompleted() != null && !updated.getCluesCompleted().equals(huntActivity.getCluesCompleted())) {
      huntActivity.setCluesCompleted(updated.getCluesCompleted());
      huntActivityRepository.save(huntActivity);
    }
    if (updated.getCompleted() != null && !updated.getCompleted().equals(huntActivity.getCompleted())) {
      huntActivity.setCompleted(updated.getCompleted());
      huntActivityRepository.save(huntActivity);
    }
      return huntActivity;
  }

  /**
   * This method returns all {@link HuntActivity} records in the database.  It may not be needed in
   * production.
   *
   * @return all Hunts grouped by {@link Organizer}.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HuntActivity> get() {
    return huntActivityRepository.getAllOrderByStarted();
  }

  /**
   * This method deletes a single {@link HuntActivity}.
   *
   * @param id is the id of the HuntActvity to be deleted.
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    huntActivityRepository.findById(id).ifPresent(huntActivityRepository::delete);
  }
}
