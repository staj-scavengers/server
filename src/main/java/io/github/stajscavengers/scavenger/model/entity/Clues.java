package io.github.stajscavengers.scavenger.model.entity;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "clue_name"),
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

  @ManyToOne(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "hunt_id")
  private Hunts hunts;

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

  @NonNull
  public UUID getId() {
    return id;
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
