package io.github.stajscavengers.scavenger.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;


  @Entity
      ( foreignKeys =
  @ForeignKey(
    entity = Hunts.class,
    parentColumns  = "hunt_id",
    childColumns = "hunter_id"
))
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
    @Column(name = "hunt_id", nullable = false, updatable = false)
    private long huntId;

    @NonNull
    public String getHunterName() {
      return hunterName;
    }

    public long getHuntId() {
      return huntId;
    }

    public void setHunterName(@NonNull String hunterName) {
      this.hunterName = hunterName;
    }
  }
