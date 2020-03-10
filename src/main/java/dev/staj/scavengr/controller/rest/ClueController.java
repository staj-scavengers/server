package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.service.ClueRepository;
import dev.staj.scavengr.service.HuntRepository;
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
 *
 */
@RestController
@RequestMapping("/clues")
@ExposesResourceFor(Clue.class)
public class ClueController {

  private final ClueRepository clueRepository;
  private final HuntRepository huntRepository;

  @Autowired
  public ClueController(ClueRepository clueRepository, HuntRepository huntRepository) {
    this.clueRepository = clueRepository;
    this.huntRepository = huntRepository;
  }

  /**
   * This method consume media to create new clues.
   * @param clue contains video,Href,articles.
   * @return clue Hrefs.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Clue> post(@RequestBody Clue clue) {
    clueRepository.save(clue);
    return ResponseEntity.created(clue.getHref()).body(clue);
  }

//  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//  public Iterable<Clue> get() {
//    return clueRepository.getAllByOrderByHuntId();
//  }

  /**
   *
   * @param hunt_id has a search method that allow user to search clues by there hunt id..
   * @return returns the list of clues .
   */
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Clue> search(@RequestParam("q") UUID hunt_id) {
    return clueRepository.getAllByHuntIdContainsOrderByHuntOrder(hunt_id);
  }

  /**
   * this method contains clue.
   * @param id get only one clue by its id.
   * @return clue.
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue get(@PathVariable UUID id) {
    return clueRepository.findOrFail(id);
  }

  /**
   *
   * @return list of clues.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Clue> getList() {
    return clueRepository.getAllByOrderByHunt();
  }

  /**
   *
   * @param id delete a clue by its id.
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    clueRepository.findById(id).ifPresent(clueRepository::delete);
  }

  /**
   * attachClue set an existing clue and attach it to hunts.
   * @param huntId is being attached by clueId.
   * @param clueId is being attached to huntId.
   * @return the hunt with an existing clue attached to it.
   */
  @PutMapping(value = "/{clueId}/hunt/{huntId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue attachClue(@PathVariable UUID huntId, @PathVariable UUID clueId) {
    Hunt hunt = huntRepository.findOrFail(huntId);
    Clue clue = clueRepository.findOrFail(clueId);
    clue.setHunt(hunt);
    clueRepository.save(clue);
    return clue;
  }

// we don't need this (I think).
//  @PutMapping(value = "/{id}/media", produces = MediaType.APPLICATION_JSON_VALUE)
//  public Clue updateMedia(@PathVariable UUID id, @RequestBody String newMedia) {
//    Clue clue = clueRepository.findOrFail(id);
//    if (!newMedia.equals(clue.getMedia())) {
//      clue.setMedia(newMedia);
//      clueRepository.save(clue);
//    }
//    return clue;
//  }

  /**
   * Edit method update and edit the clues and clue type which is media and media type which is either nfc tag or qrcode.
   * @param clueId is being used to attache any media and media tag to it.
   * @param updated updates clue name and clue instance.
   * @return clue.
   */
  @PutMapping(value = "/{clueId}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue edit(@PathVariable UUID clueId, @RequestBody Clue updated) {
    Clue clue = clueRepository.findOrFail(clueId);
    if (updated.getClueName() != null && !updated.getClueName().equals(clue.getClueName())) {
      clue.setClueName(updated.getClueName());
      clueRepository.save(clue);
    }
    if (updated.getMedia() != null && !updated.getMedia().equals(clue.getMedia())) {
      clue.setMedia(updated.getMedia());
      clueRepository.save(clue);
    }
    if (updated.getMediaTag() != null && !updated.getMediaTag().equals(clue.getMediaTag())) {
      clue.setMediaTag(updated.getMediaTag());
      clueRepository.save(clue);
    }
    if (updated.getHuntOrder() != null && !updated.getHuntOrder().equals(clue.getHuntOrder())) {
      clue.setHuntOrder(updated.getHuntOrder());
      clueRepository.save(clue);
    }
    return clue;
  }
}
