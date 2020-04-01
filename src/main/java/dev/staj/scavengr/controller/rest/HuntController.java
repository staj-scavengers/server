package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.Organizer;
import dev.staj.scavengr.service.ClueRepository;
import dev.staj.scavengr.service.HuntRepository;
import java.util.Arrays;
import dev.staj.scavengr.service.OrganizerRepository;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller Class uses HTTP and JSON values to do {@link Hunt} CRUD Operations through the
 * {@link HuntRepository}.
 *
 * @author STAJ
 */
@RestController
@RequestMapping("/hunts")
@ExposesResourceFor(Hunt.class)
public class HuntController {

  private final HuntRepository huntRepository;
  private final OrganizerRepository organizerRepository;
  private final ClueRepository clueRepository;

  /**
   * The HuntController constructor initializes the three repositories the controller needs access
   * to.
   *
   * @param huntRepository      contains methods for manipulating {@link Hunt}s.
   * @param organizerRepository contains methods for manipulating {@link Organizer}s.
   * @param clueRepository      contains methods for manipulating {@link Clue}s.
   */
  @Autowired
  public HuntController(HuntRepository huntRepository, OrganizerRepository organizerRepository,
      ClueRepository clueRepository) {
    this.huntRepository = huntRepository;
    this.organizerRepository = organizerRepository;
    this.clueRepository = clueRepository;
  }

  /**
   * This method creates a new {@link Hunt} entity.
   *
   * @param hunt contains Hunt fields.
   * @return Href address for the new Hunt.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Hunt> post(@RequestBody Hunt hunt) {
    huntRepository.save(hunt);
    return ResponseEntity.created(hunt.getHref()).body(hunt);
  }

  /**
   * This method searches for one {@link Hunt} by id.
   *
   * @param id is the Hunt's unique id.
   * @return an individual Hunt.
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Hunt get(@PathVariable UUID id) {
    return huntRepository.findOrFail(id);
  }

  /**
   * This method returns all {@link Hunt}s in the database.  It may not be needed in production.
   *
   * @return all Hunts grouped by {@link Organizer}.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Hunt> getList() {
    return huntRepository.getAllByOrderByOrganizer();
  }

  /**
   * This method searches all {@link Hunt}s based on a string.
   *
   * @param fragment is a search string entered in the url.
   * @return list of matching Hunts.
   */
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Hunt> search(@RequestParam("q") String fragment) {
    return huntRepository.getAllByHuntNameContainsOrderByHuntName(fragment);
  }

  /**
   * This method deletes a single {@link Hunt}.
   *
   * @param id is the id of the Hunt to be deleted.
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    huntRepository.findById(id).ifPresent(huntRepository::delete);
  }

  // TODO override .equals() in Clue to compare Clues more effectively.
  @PutMapping(value = "/{huntId}/clue/{clueId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Hunt putClue(@PathVariable UUID huntId, @PathVariable UUID clueId) {
    Hunt hunt = huntRepository.findOrFail(huntId);
    Clue clue = clueRepository.findOrFail(clueId);
    if (!hunt.getClues().contains(clue)) {
      hunt.addClue(clue);
      huntRepository.save(hunt);
    }
    return hunt;
  }

  @PutMapping(value = "/{huntId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Hunt editHunt(@PathVariable UUID huntId, @RequestBody Hunt updated) {
    Hunt hunt = huntRepository.findOrFail(huntId);
    if (updated.getHuntName() != null && !updated.getHuntName().equalsIgnoreCase(hunt.getHuntName())) {
      hunt.setHuntName(updated.getHuntName());
          }
    if (updated.getClues() != null) {
      for (Clue clue : updated.getClues()) {
        if (!hunt.getClues().contains(clue)) {
          hunt.addClue(clue);
        }
      }
    }
    huntRepository.save(hunt);
    return hunt;
  }

  /**
   * This method attaches an {@link Organizer} to a {@link Hunt} if it isn't already.
   *
   * @param huntId      is the id of the Hunt to be modified.
   * @param organizerId is the id of the Organizer to be attached.
   * @return the updated Hunt.
   */
  @PutMapping(value = "/{huntId}/organizer/{organizerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Hunt attach(@PathVariable UUID huntId, @PathVariable UUID organizerId) {
    Hunt hunt = huntRepository.findOrFail(huntId);
    Organizer organizer = organizerRepository.findOrFail(organizerId);
    if (!organizer.equals(hunt.getOrganizer())) {
      hunt.setOrganizer(organizer);
      huntRepository.save(hunt);
    }
    return hunt;
  }

  /**
   * This method allows a {@link Hunt} to be renamed.
   *
   * @param id       is the Hunt to be renamed
   * @param huntName being assign to hunt name.
   * @return the updated Hunt.
   */
  @PutMapping(value = "/{id}")
  public Hunt rename(@PathVariable UUID id, @RequestPart String huntName) {
    Hunt hunt = huntRepository.findOrFail(id);
    if (!huntName.equals(hunt.getHuntName())) {
      hunt.setHuntName(huntName);
      huntRepository.save(hunt);
    }
    return hunt;
  }
}
