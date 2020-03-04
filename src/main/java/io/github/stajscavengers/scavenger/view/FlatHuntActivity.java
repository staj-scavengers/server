package io.github.stajscavengers.scavenger.view;

import java.net.URI;
import java.util.Date;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface FlatHuntActivity {

  @NonNull
  UUID getHuntActivityId();

  @NonNull
  Date getStarted();

  @NonNull
  Date getCompleted();

  long getTotalTime();

  @NonNull
  Integer getCluesCompleted();

  URI getHref();
}
