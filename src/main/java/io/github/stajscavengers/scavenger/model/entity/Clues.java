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
  @Column(name = "clue_name", nullable = false)
  private String clueName;

  @NonNull
  @Column(name = "hunt_id", nullable = false, updatable = false)
  private long huntId;

  @NonNull
  @Column(nullable = false)
  private Object media;

  @NonNull
  @Column(name = "media_tag", nullable = false)
  private Object mediaTag;

  @NonNull
  @Column(name = "hunt_order", nullable = false)
  private Integer huntOrder;

  @NonNull
  public String getClueName() {
    return clueName;
  }

  public void setClueName(@NonNull String clueName) {
    this.clueName = clueName;
  }

  public long getHuntId() {
    return huntId;
  }

  public void setHuntId(long huntId) {
    this.huntId = huntId;
  }

  @NonNull
  public Object getMedia() {
    return media;
  }

  public void setMedia(@NonNull Object media) {
    this.media = media;
  }

  @NonNull
  public Object getMediaTag() {
    return mediaTag;
  }

  public void setMediaTag(@NonNull Object mediaTag) {
    this.mediaTag = mediaTag;
  }

  @NonNull
  public Integer getHuntOrder() {
    return huntOrder;
  }

  public void setHuntOrder(@NonNull Integer huntOrder) {
    this.huntOrder = huntOrder;
  }
}
