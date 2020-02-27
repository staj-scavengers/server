package io.github.stajscavengers.scavenger.controller.rest;

import io.github.stajscavengers.scavenger.model.entity.Clue;
import io.github.stajscavengers.scavenger.model.entity.HuntActivity;
import io.github.stajscavengers.scavenger.service.ClueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clues")
@ExposesResourceFor(Clue.class)
public class ClueController {

  private final ClueRepository clueRepository;

  @Autowired
  public ClueController(ClueRepository clueRepository) {
    this.clueRepository = clueRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Clue> post(@RequestBody Clue clue) {
    clueRepository.save(clue);
    return ResponseEntity.created(clue.getHref()).body(clue);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Clue> get() {
    return clueRepository.findAllByOrderByHuntOrder();
  }

}
