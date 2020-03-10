package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.Organizer;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 * this interface provides a returnable JSON {@link Clue} object that does not recursively return linked
 * entities.
 */
@JsonPropertyOrder({"id", "hunt", "hunt_order", "clue_name", "media_tag", "media"})
public interface FlatClue {

  /**
   * returns the clue's unique id.
   */
  @NonNull
  UUID getId();

  /**
   * returns the clue's name as a string.
   */
  @NonNull
  String getClueName();

  /**
   * returns the string URL of the media triggered by the clue's QR/NFC tag.
   */
  @NonNull
  String getMedia();

  /**
   * returns the string value of the QR/NFC tag associated with the clue.
   */
  @NonNull
  String getMediaTag();

  /**
   * returns the numeric order the clue will appear in in its {@link Hunt}, or null if the hunt in
   * unordered.
   */
  @NonNull
  Integer getHuntOrder();

    /**
     *
     * returns the href value for the {@link Clue}.
     */
  @NonNull
  URI getHref();

}


