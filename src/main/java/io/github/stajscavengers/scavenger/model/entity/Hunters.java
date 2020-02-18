package io.github.stajscavengers.scavenger.model.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "organizer_name")
    }
)
public class Hunters {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "hunter_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(name = "hunter_name", nullable = false)
  private String hunterName;


  @NonNull
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "hunters",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @Column(name = "hunt_id", nullable = false, updatable = false)
  private Set<Hunts> hunts = new LinkedHashSet<>();

  @NonNull
  public String getHunterName() {
    return hunterName;
  }

  @NonNull
  public Set<Hunts> getHunts() {
    return hunts;
  }

  public void setHunterName(@NonNull String hunterName) {
    this.hunterName = hunterName;
  }
}
