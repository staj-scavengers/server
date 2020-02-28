package io.github.stajscavengers.scavenger.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.stajscavengers.scavenger.model.entity.User;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "user", "href"})
public interface FlatOrganizer {

  @NonNull
  UUID getId();

  @NonNull
  Set<User> getUser();

  URI getHref();
}
