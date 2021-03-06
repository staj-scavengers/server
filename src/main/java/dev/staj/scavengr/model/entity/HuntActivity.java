package dev.staj.scavengr.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.staj.scavengr.view.FlatHunt;
import dev.staj.scavengr.view.FlatHuntActivity;
import dev.staj.scavengr.view.FlatUser;
import java.net.URI;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * HuntActivities are the join entity between {@link User}s and {@link Hunt}s, which persist the
 * record of Hunt participation.  Each instance records start and complete times, as well as number
 * of completed {@link Clue}s.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Component
    @Table(
        indexes = {
            @Index(columnList = "date_started"),
            @Index(columnList = "date_completed"),
            @Index(columnList = "clues_completed")
        }
    )
public class HuntActivity implements FlatHuntActivity {

  private static EntityLinks entityLinks;


  @NonNull
  @Id
  @Column( name = "hunt_activity_id")
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private UUID huntActivityId;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "hunt_id")
  @JsonSerialize(as = FlatHunt.class)
  private Hunt hunt;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "user_id")
  @JsonSerialize(as = FlatUser.class)
  private User user;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_started", nullable = false)
  private Date started;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_completed")
  private Date completed;

  @NonNull
  @Column(name = "clues_completed")
  private Integer cluesCompleted;

  /**
   *
   * returns hunt activity
   */
  @NonNull
  public UUID getHuntActivityId() {
    return huntActivityId;
  }

  /**
   *
   * return the time and date that a user started the hunt activity.
   */
  @NonNull
  public Date getStarted() {
    return started;
  }

  /**
   *
   * set a time for a user when started the hunt activity.
   */
  public void setStarted(@NonNull Date started) {
    this.started = started;
  }

  public String getTotalTime() {
    if(started == null || completed == null) {
      return null;
    }
    long minTotal = started.getTime() - completed.getTime();
    long seconds = minTotal / 1000;
    long minutes = seconds / 60;
    long hours = minutes / 60;
    long days = hours / 24;

    String time = days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;


    return time;
  }

  /**
   *
   * return date and time for when a hunt activity is completed
   */
  @NonNull
  public Date getCompleted() {
    return completed;
  }

  /**
   *
   * Sets Date and time for when a user completed the hunt activity.
   */
  public void setCompleted(@NonNull Date completed) {
    this.completed = completed;
  }

  /**
   * return the number of how many clues have been completed
   */
  public Integer getCluesCompleted() {
    return cluesCompleted;
  }

  /**
   *
   * set cluesCompleted for the clues that has done.
   */
  public void setCluesCompleted(int cluesCompleted) {
    this.cluesCompleted = cluesCompleted;
  }

  /**
   *
   * return entity links for hunt activity.
   */
  public URI getHref() {
    return entityLinks.linkForItemResource(HuntActivity.class, huntActivityId).toUri();
  }
  @PostConstruct
  private void init() {
    entityLinks.toString();
  }

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    HuntActivity.entityLinks = entityLinks;
  }

  @Override
  public String toString() {
    return (hunt.getHuntName() + " | " + user.getUserName());
  }
}
