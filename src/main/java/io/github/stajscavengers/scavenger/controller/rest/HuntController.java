package io.github.stajscavengers.scavenger.controller.rest;
import io.github.stajscavengers.scavenger.model.entity.Clue;
import io.github.stajscavengers.scavenger.model.entity.Hunt;
import io.github.stajscavengers.scavenger.model.entity.Organizer;
import io.github.stajscavengers.scavenger.service.ClueRepository;
import io.github.stajscavengers.scavenger.service.HuntRepository;
import io.github.stajscavengers.scavenger.service.OrganizerRepository;
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
 public ResponseEntity<Hunt> post(@RequestBody Hunt hunt) {
  huntRepository.save(hunt);
  return ResponseEntity.created(hunt.getHref()).body(hunt);
 }

 @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
 // Changed from getByOrganizer(long id) to this. --Trace
 public Hunt getByOrganizer(UUID id) {
  return huntRepository.getByOrganizer(id).get();
 }

// @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
// public Iterable<Hunt> get() {
//  return huntRepository.getAllByOrderByOrganizer();
// }

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
 public Clue attachClue(@PathVariable UUID huntId, @PathVariable UUID clueId) {
  Hunt hunt = huntRepository.findOrFail(huntId);
  Clue clue = clueRepository.findOrFail(clueId);
  clue.setHunt(hunt);
  clueRepository.save(clue);
  return clue;
 }

 @PutMapping(value = "huntId",
     consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 public Hunt edit(@PathVariable UUID huntId, @RequestBody Hunt updated) {
  Hunt hunt = huntRepository.findOrFail(huntId);
  if (updated.getHuntName() != null && !updated.getHuntName().equals(hunt.getHuntName())) {
   hunt.setHuntName(updated.getHuntName());
   huntRepository.save(hunt);
  }
  // Not entirely sure if this should be here.
  if (updated.getOrganizer() != null && !updated.getOrganizer().equals(hunt.getOrganizer())) {
   hunt.setOrganizer(updated.getOrganizer());
   huntRepository.save(hunt);
  }
  return hunt;
 }

// @PutMapping(value = "/{huntId}/organizer/{organizerId}", produces = MediaType.APPLICATION_JSON_VALUE)
// public Hunt attach(@PathVariable UUID huntId, @PathVariable UUID organizerId) {
//  Hunt hunt = huntRepository.findOrFail(huntId);
//  Organizer organizer = organizerRepository.findOrFail(organizerId);
//  if (!organizer.equals(hunt.getOrganizer())) {
//   hunt.setOrganizer(organizer);
//   huntRepository.save(hunt);
//  }
//  return hunt;
// }
//
}
