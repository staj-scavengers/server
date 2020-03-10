package dev.staj.scavengr.view;

import java.net.URI;
import java.util.Date;
import org.springframework.lang.NonNull;

public interface FlatHuntActivity {

  @NonNull
  long getHuntActivityId();

  @NonNull
  Date getStarted();

  @NonNull
  Date getCompleted();

  long getTotalTime();

  @NonNull
  Integer getCluesCompleted();

  URI getHref();
}
