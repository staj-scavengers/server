package io.github.stajscavengers.scavenger.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
@Table(
    indexes = {
        @Index(columnList = "hunt_id"),
        @Index(columnList = "hunt_name"),
        @Index(columnList = "organizer_id"),
        @Index(columnList = "clues_id"),
        @Index(columnList = "hunter_id"),
    }
)
public class Hunts {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "hunt_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(name = "hunt_name", length = 1024, nullable = false, unique = true)
  private String huntName;

  // Foreign key
  @NonNull
  @Column(name = "organizer_id", nullable = false, updatable = false)
  private long organizer;

  // Foreign key
  @NonNull
  @Column(name = "clues_id", nullable = false, updatable = false)
  private long clues;

  // Foreign key
  @NonNull
  @Column(name = "hunter_id", nullable = false, updatable = false)
  private long hunter;

  public long getId() {
    return id;
  }

  @NonNull
  public String getHuntName() {
    return huntName;
  }

  public void setHuntName(@NonNull String huntName) {
    this.huntName = huntName;
  }

  public long getOrganizer() {
    return organizer;
  }

  public long getClues() {
    return clues;
  }

  public long getHunter() {
    return hunter;
  }
}
