package io.github.stajscavengers.scavenger.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URI;
import java.util.UUID;
import javax.annotation.PostConstruct;
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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.stereotype.Component;


@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Component
@Table(
    // TODO we need a way to make sure that each hunt has unique hunt order values,
    //  but still let multiple "Clue 1"s, etc. exist in different hunts.
    uniqueConstraints =
    @UniqueConstraint(columnNames = {"hunt_id", "hunt_order"}),
    indexes = {
        @Index(columnList = "clue_name"),
//        @Index(columnList = "hunt_id"),
//        @Index(columnList = "hunt_order"),
        @Index(columnList = "media_tag")
    }
)
public class Clue {

  private static EntityLinks entityLinks;

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "clue_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(name = "clue_name", nullable = false)
  private String clueName;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @ManyToOne(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "hunt_id")
  private Hunt hunt;

  @NonNull
  @Column(nullable = false)
  private String media;

  @NonNull
  @Column(name = "media_tag", nullable = false)
  private String mediaTag;

  @NonNull
  @Column(name = "hunt_order", nullable = false)
  private Integer huntOrder;

  public Hunt getHunt() {
    return hunt;
  }

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
  public String getMedia() {
    return media;
  }

  public void setMedia(@NonNull String media) {
    this.media = media;
  }

  @NonNull
  public String getMediaTag() {
    return mediaTag;
  }

  public void setMediaTag(@NonNull String mediaTag) {
    this.mediaTag = mediaTag;
  }

  @NonNull
  public Integer getHuntOrder() {
    return huntOrder;
  }

  public void setHuntOrder(Integer huntOrder) {
    this.huntOrder = huntOrder;
  }

  public void setHunt(Hunt hunt) {
    this.hunt = hunt;
  }

  public URI getHref() {
    return entityLinks.linkForItemResource(Clue.class, id).toUri();
  }

  @PostConstruct
  private void init() {
    entityLinks.toString();
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Clue.entityLinks = entityLinks;
  }
}
