package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.Organizer;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 * this interface provides a returnable JSON {@link Hunt} object that does not recursively return linked entities
 */
@JsonPropertyOrder({"id", "hunt_name"})
public interface FlatHunt {

  /**
   * returns the hunt's unique id.
   */
  @NonNull
  UUID getId();

  /**
   *
   * returns the hunt's name as a string.
   */
  @NonNull
  String getHuntName();

  /**
   *
   * returns the href value of the {@link Hunt}.
   */
  URI getHref();

}
