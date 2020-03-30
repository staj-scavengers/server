package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.User;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 * this interface provides a returnable JSON {@link User} object that does not recursively return linked entities
 */
@JsonPropertyOrder({"id", "oauth_token", "user_name"})
public interface FlatUser {

  /**
   *
   * returns the user's unique id.
   */
  @NonNull
  UUID getId();

  /**
   *
   * returns the oAuth token for a particular user.
   */
  @NonNull
  String getOauthToken();

  /**
   *
   * returns the user's name as a string.
   */
  @NonNull
  String getUserName();

  /**
   *
   * returns the href value of the {@link User}.
   */
  URI getHref();
}
