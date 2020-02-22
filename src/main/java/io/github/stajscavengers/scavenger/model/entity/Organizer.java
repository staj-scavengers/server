package io.github.stajscavengers.scavenger.model.entity;


import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Organizer {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "organizer_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

// ForeignKey
  @NonNull
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @Column(name = "user_id", nullable = false, updatable = false)
  private Set<User> user = new LinkedHashSet<>();

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public Set<User> getUser() {
    return user;
  }
}
