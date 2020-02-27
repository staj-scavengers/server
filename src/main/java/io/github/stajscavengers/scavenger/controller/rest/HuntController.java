package io.github.stajscavengers.scavenger.controller.rest;

import io.github.stajscavengers.scavenger.model.entity.Hunt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.server.ExposesResourceFor;

@RestController
@RequestMapping("/hunts")
@ExposesResourceFor(Hunt.class)
public class HuntController {



}
