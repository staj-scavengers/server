package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.Organizer;
import dev.staj.scavengr.model.entity.User;
import dev.staj.scavengr.service.HuntRepository;
import dev.staj.scavengr.service.OrganizerRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller Class uses HTTP and JSON values to do CRUD operations through the {@link
 * OrganizerRepository}.
 *
 * @author STAJ
 */
@RestController
@RequestMapping("/organizers")
@ExposesResourceFor(Organizer.class)
public class OrganizerController {

  private final OrganizerRepository organizerRepository;
  private final UserRepository userRepository;
  private final HuntRepository huntRepository;

  /**
   * The OrganizerController constructor initializes the repositories the controller needs
   * access to.
   *
   * @param organizerRepository contains methods for manipulating {@link Organizer}s
   * @param userRepository      contains methods for manipulating {@link User}s.
   * @param huntRepository      contains methods for manipulating {@link Hunt}s.
   */
  @Autowired
  public OrganizerController(OrganizerRepository organizerRepository,
      UserRepository userRepository, HuntRepository huntRepository) {
    this.organizerRepository = organizerRepository;
    this.userRepository = userRepository;
    this.huntRepository = huntRepository;
  }

  /**
   * This method creates a new {@link Organizer} entity.
   *
   * @param organizer contains Organizer fields.
   * @return Href address for the new Organizer.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Organizer> post(@RequestBody Organizer organizer) {
    organizerRepository.save(organizer);
    return ResponseEntity.created(organizer.getHref()).body(organizer);
  }

  /**
   * This method returns all {@link Organizer}s in the database.  It may not be needed in
   * production.
   *
   * @return all Organizers.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Organizer> get() {
    return organizerRepository.getAllBy();
  }

  /**
   * This method searches for one {@link Organizer} by id.
   *
   * @param id is the Organizer's unique id.
   * @return an individual Organizer.
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Organizer get(@PathVariable UUID id) {
    return organizerRepository.findOrFail(id);
  }

  @GetMapping(value = "/{id}/hunts", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Hunt> getByOrganizer(@PathVariable UUID id) {
    return organizerRepository.findOrFail(id).getHunts();
  }

  /**
   * This method deletes a single {@link Organizer}.
   *
   * @param id is the id of the Organizer to be deleted.
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    organizerRepository.findById(id).ifPresent(organizerRepository::delete);
  }
}
