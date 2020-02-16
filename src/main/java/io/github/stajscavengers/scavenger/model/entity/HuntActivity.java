package io.github.stajscavengers.scavenger.model.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@Entity(
 foreignKeys =
 @ForeignKey(
     entity = Hunts.class,
      parentColumns = "hunt_id",
      childColumns = "hunt_activity_id")
 @ForeignKey(
        entity = Hunters.class,
        parentColumns  = "hunter_id",
        childColumns = "hunter_activity_id"
    ))
public class HuntActivity {

  @NonNull
  @Id
  @Column( name = "hunt_activity_id")
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private long huntActivityId;

  @NonNull
  @Column(name = "hunt_id")
  private long huntId;

  @NonNull
  @Column(name = "hunter_id")
  private long hunterId;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_started")
  private Date started;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_completed")
  private Date completed;

  @NonNull
  private long totalDone;

  @NonNull
  private Integer cluesCompleted;

}
