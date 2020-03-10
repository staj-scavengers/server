package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "oauth_token", "user_name", "href"})
public interface FlatUser {

  @NonNull
  UUID getId();

  @NonNull
  String getoAuthToken();

  @NonNull
  String getUserName();

  URI getHref();
}
