package io.github.stajscavengers.scavenger.controller.rest;

import io.github.stajscavengers.scavenger.model.entity.Hunt;
import io.github.stajscavengers.scavenger.model.entity.HuntActivity;
import io.github.stajscavengers.scavenger.service.HuntActivityRepository;
import io.github.stajscavengers.scavenger.service.HuntRepository;
import io.github.stajscavengers.scavenger.service.UserRepository;
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
@RequestMapping("/hunt_activity")
@ExposesResourceFor(HuntActivity.class)
public class  HuntActivityController {

  private final HuntActivityRepository huntActivityRepository;
  private final HuntRepository huntRepository;
  private final UserRepository userRepository;

  @Autowired
  public HuntActivityController(HuntActivityRepository huntActivityRepository,
      HuntRepository huntRepository, UserRepository userRepository){
    this.huntActivityRepository = huntActivityRepository;
    this.huntRepository = huntRepository;
    this.userRepository = userRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HuntActivity> post(@RequestBody HuntActivity huntActivity) {
    huntActivityRepository.save(huntActivity);
    return ResponseEntity.created(huntActivity.getHref()).body(huntActivity);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HuntActivity> get() {
    return huntActivityRepository.getAllByOrderByHunt();
  }
}
