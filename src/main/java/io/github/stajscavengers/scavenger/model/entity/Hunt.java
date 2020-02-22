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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(
    indexes = {
        @Index(columnList = "hunt_name"),
    }
)
public class Hunt {

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
  private Organizer organizer;


  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public String getHuntName() {
    return huntName;
  }

  public void setHuntName(@NonNull String huntName) {
    this.huntName = huntName;
  }

  @NonNull
  public Organizer getOrganizer() {
    return organizer;
  }

}
