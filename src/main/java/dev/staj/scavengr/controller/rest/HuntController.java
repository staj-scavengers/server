package dev.staj.scavengr.controller.rest;

import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.Organizer;
import dev.staj.scavengr.service.ClueRepository;
import dev.staj.scavengr.service.OrganizerRepository;
import dev.staj.scavengr.service.HuntRepository;
import java.util.Arrays;
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

@RestController
@RequestMapping("/hunts")
@ExposesResourceFor(Hunt.class)
public class HuntController {

  private final HuntRepository huntRepository;
  private final OrganizerRepository organizerRepository;
  private final ClueRepository clueRepository;

  @Autowired
  public HuntController(HuntRepository huntRepository, OrganizerRepository organizerRepository,
      ClueRepository clueRepository) {
    this.huntRepository = huntRepository;
    this.organizerRepository = organizerRepository;
    this.clueRepository = clueRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Hunt> post(@RequestBody Hunt hunt) {
    huntRepository.save(hunt);
    return ResponseEntity.created(hunt.getHref()).body(hunt);
  }



  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Hunt> getByOrganizer(@PathVariable UUID id) {
    return huntRepository.getAllByOrganizer(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Hunt> getList() {
    return huntRepository.getAllByOrderByOrganizer();
  }

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Hunt> search(@RequestParam("q") String fragment) {
    return huntRepository.getAllByHuntNameContainsOrderByHuntName(fragment);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    huntRepository.findById(id).ifPresent(huntRepository::delete);
  }

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

  @PutMapping(value = "/{huntId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Hunt setClues(@PathVariable UUID huntId, @RequestBody Clue... clues) {
    Hunt hunt = huntRepository.findOrFail(huntId);
      hunt.setClues(Arrays.asList(clues));
      huntRepository.save(hunt);
    return hunt;
  }

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
