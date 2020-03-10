package io.github.stajscavengers.scavenger.model.entity;


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
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Component
public class Organizer {

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
  @OneToMany(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @Column(name = "user_id", nullable = false, updatable = false)
  private Set<User> user = new LinkedHashSet<>();

  /**
   * returns organizer id.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   *
   * set a user for an organizer
   */
  @NonNull
  public Set<User> getUser() {
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
