package dev.staj.scavengr.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.staj.scavengr.view.FlatClue;
import dev.staj.scavengr.view.FlatHunt;
import dev.staj.scavengr.view.FlatOrganizer;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Component
@Table(
    indexes = {
        @Index(columnList = "hunt_name"),
    }
)
/**
 * Hunt class contains two foreign keys and one index.
 * foreign keys are organizer and clues, and there is a uuid for the hunt id which is generated Automatically
 * and it has a huntName index.
 */
public class Hunt implements FlatHunt {

  private static EntityLinks entityLinks;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "hunt_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(name = "hunt_name", length = 1024, nullable = false, unique = true)
  private String huntName;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
  )
  @JoinColumn(name = "organizer_id")
  @JsonSerialize(as = FlatOrganizer.class)
  private Organizer organizer;

  @OneToMany(mappedBy = "hunt", cascade = {CascadeType.ALL})
  @JsonSerialize(contentAs = FlatClue.class)
  private List<Clue> clues = new LinkedList<>();



  /**
   * get id for each hunt
   *
   * @return
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   * get hunt by there name.
   *
   * @return
   */
  @NonNull
  public String getHuntName() {
    return huntName;
  }

  /**
   * sethuntName is used to assign name to any hunt.
   */
  public void setHuntName(@NonNull String huntName) {
    this.huntName = huntName;
  }

  /**
   * return organizer
   */
  @NonNull
  public Organizer getOrganizer() {
    return organizer;
  }

  /**
   * sets organizer for each hunt.
   */
  public void setOrganizer(Organizer organizer) {
    this.organizer = organizer;
  }

  public List<Clue> getClues() {
    return new ArrayList<>(clues);
  }

  public void setClues(List<Clue> clues) {
    this.clues = clues;
    for (Clue clue : clues) {
      clue.setHunt(this);
    }
  }

  public void addClue(Clue clue) {
    clues.add(clue);
    clue.setHunt(this);
  }

  /**
   * get entity links
   */
  public URI getHref() {
    return entityLinks.linkForItemResource(Hunt.class, id).toUri();
  }

  @PostConstruct
  private void init() {
    entityLinks.toString();
  }

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Hunt.entityLinks = entityLinks;
  }

  @Override
  public String toString() {
    return (huntName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizer, huntName);
  }

  @Override
  public boolean equals(Object obj) {
    boolean comparison = false;
    if (obj == this) {
      comparison = true;
    } else if (obj instanceof Clue && obj.hashCode() == hashCode()) {
      Hunt other = (Hunt) obj;
      if (organizer == other.getOrganizer()
          && huntName.equals(other.getHuntName())
          && clues == other.getClues()) {
        comparison = true;
      }
    }
    return comparison;
  }
}
