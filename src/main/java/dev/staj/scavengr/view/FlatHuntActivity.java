package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.HuntActivity;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 * this interface provides a returnable JSON {@link HuntActivity} object that does not recursively return linked entities
 */
@JsonPropertyOrder({"id", "date_started", "date_completed", "clues_completed", "total_time"})
public interface FlatHuntActivity {

  /**
   *
   * returns the hunt activity's unique id.
   */
  @NonNull
  UUID getHuntActivityId();

  /**
   *
   * returns the date a particular hunt was started by a particular user.
   */
  @NonNull
  Date getStarted();

  /**
   *
   * returns the date a particular hunt was completed by a particular user.
   */
  Date getCompleted();

  /**
   *
   * returns the difference between the started and completed values for a particular hunt by a particular user.
   */
  long getTotalTime();

  /**
   *
   * returns the number of clues completed in a particular hunt by a particular user.
   */
  @NonNull
  Integer getCluesCompleted();

  /**
   *
   * returns the href value of the {@link HuntActivity}.
   */
  URI getHref();
}
