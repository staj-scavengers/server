package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.repository.ClueRepository;
import dev.staj.scavengr.model.repository.HuntRepository;
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

/**
 * This Controller Class uses HTTP and JSON values to do {@link Clue} CRUD Operations through the
 * {@link ClueRepository}.
 *
 * @author STAJ
 */
@RestController
@RequestMapping("/clues")
@ExposesResourceFor(Clue.class)
public class ClueController {

  private final ClueRepository clueRepository;
  private final HuntRepository huntRepository;

  /**
   * The ClueController constructor initializes the two repositories the controller needs access
   * to.
   *
   * @param clueRepository contains methods for manipulating {@link Clue}s.
   * @param huntRepository contains methods for manipulating {@link Hunt}s.
   */
  @Autowired
  public ClueController(ClueRepository clueRepository, HuntRepository huntRepository) {
    this.clueRepository = clueRepository;
    this.huntRepository = huntRepository;
  }

  /**
   * This method creates a new {@link Clue}.
   *
   * @param clue contains Clue fields.
   * @return Href address for the new Clue.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Clue> post(@RequestBody Clue clue) {
    clueRepository.save(clue);
    return ResponseEntity.created(clue.getHref()).body(clue);
  }

  /**
   * This method returns all {@link Clue}s for one {@link Hunt}.
   *
   * @param hunt is the Hunt to search by
   * @return an {@link Iterable}<{@link Clue}> collection.
   */
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Clue> search(@RequestParam("q") Hunt hunt) {
    return clueRepository.getAllByHuntOrderByHuntOrder(hunt);
  }

  /**
   * this method searches for one {@link Clue} by id.
   *
   * @param id is the {@link Clue}'s unique id.
   * @return an individual Clue.
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue get(@PathVariable UUID id) {
    return clueRepository.findOrFail(id);
  }

  /**
   * This method returns all {@link Clue}s in the database.  It may not be needed in production.
   *
   * @return an {@link Iterable}<{@link Clue}> collection.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Clue> getList() {
    return clueRepository.getAllByOrderByHunt();
  }

  /**
   * This method deletes a single {@link Clue}.
   *
   * @param id is the id of the Clue to be deleted.
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    clueRepository.findById(id).ifPresent(clueRepository::delete);
  }

  /**
   * This method attaches a {@link Clue} to a {@link Hunt}.
   *
   * @param huntId is the id of the Hunt.
   * @param clueId is the id of the Clue to be modified.
   * @return the updated Clue.
   */
  @PutMapping(value = "/{clueId}/hunt/{huntId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue attachClue(@PathVariable UUID huntId, @PathVariable UUID clueId) {
    Hunt hunt = huntRepository.findOrFail(huntId);
    Clue clue = clueRepository.findOrFail(clueId);
    clue.setHunt(hunt);
    clueRepository.save(clue);
    return clue;
  }

  /**
   * This method modifies the fields of an existing {@link Clue} object.  By checking "is not null"
   * and "does not already match" for each field before updating, it is able to handle redundant and
   * incomplete requests without failing.
   *
   * @param id      is the Clue to be modified.
   * @param updated contains new Clue field values.
   * @return the modified Clue.
   */
  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue edit(@PathVariable UUID id, @RequestBody Clue updated) {
    Clue clue = clueRepository.findOrFail(id);
    if (updated.getClueName() != null && !updated.getClueName().equals(clue.getClueName())) {
      clue.setClueName(updated.getClueName());
    }
    if (updated.getMedia() != null && !updated.getMedia().equals(clue.getMedia())) {
      clue.setMedia(updated.getMedia());
    }
    if (updated.getMediaTag() != null && !updated.getMediaTag().equals(clue.getMediaTag())) {
      clue.setMediaTag(updated.getMediaTag());
    }
    if (updated.getHuntOrder() != null && !updated.getHuntOrder().equals(clue.getHuntOrder())) {
      clue.setHuntOrder(updated.getHuntOrder());
    }
    clueRepository.save(clue);
    return clue;
  }
}
