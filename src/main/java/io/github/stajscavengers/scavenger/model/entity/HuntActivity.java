package io.github.stajscavengers.scavenger.model.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
    @Table(
        indexes = {
            @Index(columnList = "date_started"),
            @Index(columnList = "date_completed"),
            @Index(columnList = "total_time"),
            @Index(columnList = "clues_completed")
        }
    )
public class HuntActivity {

  @NonNull
  @Id
  @Column( name = "hunt_activity_id")
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private long huntActivityId;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "hunt_id")
  private Hunt hunt;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "user_id")
  private User user;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_started")
  private Date started;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_completed")
  private Date completed;

  @NonNull
  private long totalTime;

  @NonNull
  private Integer cluesCompleted;

  @NonNull
  public Date getStarted() {
    return started;
  }

  public void setStarted(@NonNull Date started) {
    this.started = started;
  }

  @NonNull
  public Date getCompleted() {
    return completed;
  }

  public void setCompleted(@NonNull Date completed) {
    this.completed = completed;
  }

  public long getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(long totalTime) {
    this.totalTime = totalTime;
  }

  @NonNull
  public Integer getCluesCompleted() {
    return cluesCompleted;
  }

  public void setCluesCompleted(@NonNull Integer cluesCompleted) {
    this.cluesCompleted = cluesCompleted;
  }
}
