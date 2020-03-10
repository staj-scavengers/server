package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.Organizer;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "hunt_name", "href"})
public interface FlatHunt {


  @NonNull
  UUID getId();

  @NonNull
  String getHuntName();

  URI getHref();

}
