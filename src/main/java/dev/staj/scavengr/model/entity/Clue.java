package dev.staj.scavengr.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.staj.scavengr.view.FlatClue;
import dev.staj.scavengr.view.FlatHunt;
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
import org.springframework.stereotype.Component;



/**
 * Clue Entity has four indexes clue_name, hunt_id, hunt_order, media_tag.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Component
@Table(
    uniqueConstraints =
    @UniqueConstraint(columnNames = {"hunt_id", "hunt_order"}),
    indexes = {
        @Index(columnList = "clue_name"),
        @Index(columnList = "media_tag")
    }
)
public class Clue implements FlatClue {


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
  @JoinColumn(name = "hunt_id", nullable = false)
  @JsonSerialize(as = FlatHunt.class)
  private Hunt hunt;

  @NonNull
  @Column(nullable = false)
  private String media;

  @NonNull
  @Column(name = "media_tag", nullable = false)
  private String mediaTag;

  @NonNull
  @Column(name = "hunt_order")
  private Integer huntOrder;

  /**
   *
   * @return getHunt gets the Hunts Object.
   */
  public Hunt getHunt() {
    return hunt;
  }

  /**
   * Returns the clue name for this instance.
   */
  @NonNull
  public String getClueName() {
    return clueName;
  }

  /**
   * Sets the clue name for this instance.
   */
  public void setClueName(@NonNull String clueName) {
    this.clueName = clueName;
  }

  /**
   *
   * @return getId returns clue id.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   *
   * @return getMedia returns media to user when scanned the QRCode || NFC tags.
   */
  @NonNull
  public String getMedia() {
    return media;
  }

  /**
   *
   * @param media Set the media for any clue in the entity.
   */
  public void setMedia(@NonNull String media) {
    this.media = media;
  }

  /**
   *
   * @return getMediaTag returns the appropriate NFC tag and or QRCode.
   */
  @NonNull
  public String getMediaTag() {
    return mediaTag;
  }

  /**
   *
   * @param mediaTag set MediaTag to appropriate Clue.
   */
  public void setMediaTag(@NonNull String mediaTag) {
    this.mediaTag = mediaTag;
  }

  /**
   *
   * @return hunt order.
   */
  @NonNull
  public Integer getHuntOrder() {
    return huntOrder;
  }

  /**
   *
   * @param huntOrder sets hunts in given order.
   */
  public void setHuntOrder(Integer huntOrder) {
    this.huntOrder = huntOrder;
  }

  /**
   *
   * @param hunt setHunt sets hunt for the User.
   */
  public void setHunt(Hunt hunt) {
    this.hunt = hunt;
  }

  /**
   *
   * @return getHref returns the Link to the CLue.
   */
  public URI getHref() {
    return entityLinks.linkForItemResource(Clue.class, id).toUri();
  }

  /**
   * Converts entity links to string value.
   */
  @PostConstruct
  private void init() {
    entityLinks.toString();
  }

  /**
   *
   * @param entityLinks Generate an entity links value.
   */
  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Clue.entityLinks = entityLinks;
  }
}
