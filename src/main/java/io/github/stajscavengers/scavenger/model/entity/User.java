package io.github.stajscavengers.scavenger.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class User {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(name = "oauth_token", nullable = false, updatable = false)
  private String oAuthToken;

  @NonNull
  @Column(name = "is_organizer")
  private boolean isOrganizer;

  @NonNull
  public String getoAuthToken() {
    return oAuthToken;
  }

  public void setoAuthToken(@NonNull String oAuthToken) {
    this.oAuthToken = oAuthToken;
  }

  public boolean isOrganizer() {
    return isOrganizer;
  }

  public void setOrganizer(boolean organizer) {
    isOrganizer = organizer;
  }
}
