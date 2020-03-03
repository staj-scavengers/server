package io.github.stajscavengers.scavenger.controller.rest;

import io.github.stajscavengers.scavenger.model.entity.Clue;
import io.github.stajscavengers.scavenger.model.entity.Hunt;
import io.github.stajscavengers.scavenger.model.entity.HuntActivity;
import io.github.stajscavengers.scavenger.model.entity.User;
import io.github.stajscavengers.scavenger.service.ClueRepository;
import io.github.stajscavengers.scavenger.service.HuntRepository;
import java.util.Set;
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

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Clue> post(@RequestBody Clue clue) {
    clueRepository.save(clue);
    return ResponseEntity.created(clue.getHref()).body(clue);
  }

//  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//  public Iterable<Clue> get() {
//    return clueRepository.getAllByOrderByHuntId();
//  }

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Clue> search(@RequestParam("q") UUID hunt_id) {

    return clueRepository.getAllByHuntIdContainsOrderByHuntOrder(hunt_id);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue get(@PathVariable UUID id) {
    return clueRepository.findOrFail(id);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    clueRepository.findById(id).ifPresent(clueRepository::delete);
  }

  @PutMapping(value = "/{id}/media", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clue updateMedia(@PathVariable UUID id, @RequestBody String newMedia) {
    Clue clue = clueRepository.findOrFail(id);
    if (!newMedia.equals(clue.getMedia())) {
      clue.setMedia(newMedia);
      clueRepository.save(clue);
    }
    return clue;
  }

}
