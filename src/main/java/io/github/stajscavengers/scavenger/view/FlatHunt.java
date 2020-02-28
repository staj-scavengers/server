package io.github.stajscavengers.scavenger.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.stajscavengers.scavenger.model.entity.Organizer;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "hunt_name", "organizer", "href"})
public interface FlatHunt {


  @NonNull
  UUID getId();

  @NonNull
  String getHuntName();

  @NonNull
  Organizer getOrganizer();

  URI getHref();

}
