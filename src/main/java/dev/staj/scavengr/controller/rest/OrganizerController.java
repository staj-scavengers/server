package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.Organizer;
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

@RestController
@RequestMapping("/organizers")
@ExposesResourceFor(Organizer.class)
public class OrganizerController {

  private final OrganizerRepository organizerRepository;
  private final UserRepository userRepository;

  /**
   * Initializes {@link OrganizerRepository} and {@link UserRepository}
   * @param organizerRepository
   * @param userRepository
   */
  @Autowired
  public OrganizerController (OrganizerRepository organizerRepository, UserRepository userRepository) {
    this.organizerRepository = organizerRepository;
    this.userRepository = userRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Organizer> post(@RequestBody Organizer organizer) {
    organizerRepository.save(organizer);
    return ResponseEntity.created(organizer.getHref()).body(organizer);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Organizer> get() {
    return organizerRepository.getAllByOrderById();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Organizer get(@PathVariable UUID id) {
    return organizerRepository.findOrFail(id);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    organizerRepository.findById(id).ifPresent(organizerRepository::delete);
  }
}
