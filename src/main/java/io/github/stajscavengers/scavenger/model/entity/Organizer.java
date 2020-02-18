package io.github.stajscavengers.scavenger.model.entity;


import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
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
public class Organizer {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "organizer_id", columnDefinition = "CHAR(16) FOR BIT DATA",
  nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(name = "organizer_name", nullable = false)
  private String organizerName;

// ForeignKey
  @NonNull
  @Column(name = "hunt_id", nullable = false, updatable = false)
  private long huntId;

  @NonNull
  @Column(name = "organizer_name", nullable = false)
  public String getOrganizerName() {
    return organizerName;
  }

  public void setOrganizerName(@NonNull String organizerName) {
    this.organizerName = organizerName;
  }

  public long getHuntId() {
    return huntId;
  }

}
