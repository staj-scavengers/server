package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.User;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "user", "href"})
public interface FlatOrganizer {

  @NonNull
  UUID getId();

  URI getHref();
}
