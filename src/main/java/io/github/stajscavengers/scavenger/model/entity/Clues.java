package io.github.stajscavengers.scavenger.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
@Table(
    indexes = {
        @Index(columnList = "clue name"),
        @Index(columnList = "hunt_id"),
        @Index(columnList = "hunt_order", unique = true),
        @Index(columnList = "media"),
        @Index(columnList = "media_tag"),
    }
)
public class Clues {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "clue_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(nullable = false, updatable = true)
  private String clue_name;

  @NonNull
  @Column(nullable = false, updatable = false)
  private long hunt_id;

  @NonNull
  @Column(nullable = false)
  private Object media;

  @NonNull
  @Column(nullable = false)
  private Object media_tag;

  @NonNull
  @Column(nullable = false)
  private Integer hunt_order;

  @NonNull
  public String getClue_name() {
    return clue_name;
  }

  public void setClue_name(@NonNull String clue_name) {
    this.clue_name = clue_name;
  }

  public long getHunt_id() {
    return hunt_id;
  }

  public void setHunt_id(long hunt_id) {
    this.hunt_id = hunt_id;
  }

  @NonNull
  public Object getMedia() {
    return media;
  }

  public void setMedia(@NonNull Object media) {
    this.media = media;
  }

  @NonNull
  public Object getMedia_tag() {
    return media_tag;
  }

  public void setMedia_tag(@NonNull Object media_tag) {
    this.media_tag = media_tag;
  }

  @NonNull
  public Integer getHunt_order() {
    return hunt_order;
  }

  public void setHunt_order(@NonNull Integer hunt_order) {
    this.hunt_order = hunt_order;
  }
}
