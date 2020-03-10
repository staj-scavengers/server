package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.Organizer;
import dev.staj.scavengr.model.entity.User;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 * this interface provides a returnable JSON value that does not recursively return linked entities
 */
@JsonPropertyOrder({"id", "user"})
public interface FlatOrganizer {

  /**
   *
   * returns the organizer's unique id.
   */
  @NonNull
  UUID getId();

  /**
   *
   * returns the href value of the {@link Organizer}.
   */
  URI getHref();
}
