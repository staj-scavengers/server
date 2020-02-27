package io.github.stajscavengers.scavenger.controller.rest;

import io.github.stajscavengers.scavenger.model.entity.Clue;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clues")
@ExposesResourceFor(Clue.class)
public class ClueController {

}
