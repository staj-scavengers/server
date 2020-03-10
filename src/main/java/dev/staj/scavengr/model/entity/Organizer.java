package dev.staj.scavengr.model.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.staj.scavengr.view.FlatHunt;
import dev.staj.scavengr.view.FlatOrganizer;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Component
public class Organizer implements FlatOrganizer {

  private static EntityLinks entityLinks;

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "organizer_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

// ForeignKey

  @NonNull
  @OneToOne(mappedBy = "organizer")
  private User user;

  @OneToMany(mappedBy = "organizer", cascade = {CascadeType.ALL})
  @JsonSerialize(as = FlatHunt.class)
  private Set<Hunt> hunts = new LinkedHashSet<>();

  /**
   * returns organizer id.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  public Set<Hunt> getHunts() {
    return hunts;
  }

  /**
   *
   * set a user for an organizer
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * get the entity links for organizer.
   */
  public URI getHref() {
    return entityLinks.linkForItemResource(Hunt.class, id).toUri();
  }

  @PostConstruct
  private void init() {
    entityLinks.toString();
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Organizer.entityLinks = entityLinks;
  }

}
