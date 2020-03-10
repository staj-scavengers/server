package dev.staj.scavengr.model.entity;

import dev.staj.scavengr.view.FlatUser;
import java.net.URI;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@SuppressWarnings("JpaDataSourceORMInspection")
@Component
@Entity
@Table(name = "user_profile")
public class User implements FlatUser {

  private static EntityLinks entityLinks;

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @Column(name = "oauth_token", nullable = true, updatable = false)
  private String oAuthToken;

   @NonNull
  @Column(name = "user_name", nullable = false)
  private String userName;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "organizer_id", referencedColumnName = "organizer_id")
  private Organizer organizer;

  /**
   * return user id.
   * */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   *
   * return oauthtoken for each user.
   */
  @NonNull
  public String getoAuthToken() {
    return oAuthToken;
  }

  /**
   * set an oauthToken for each user.
   * @param oAuthToken
   */
  public void setoAuthToken(@NonNull String oAuthToken) {
    this.oAuthToken = oAuthToken;
  }

  /**
   *
   * return user name.
   */
  @NonNull
  public String getUserName() {
    return userName;
  }

  /**
   * sets userName
   */
  public void setUserName(@NonNull String userName) {
    this.userName = userName;
  }

  public Organizer getOrganizer() {
    return organizer;
  }

  public void setOrganizer(Organizer organizer) {
    this.organizer = organizer;
  }

  /**
   *
   * return the entity links for each user.
   */
  public URI getHref() {
    return entityLinks.linkForItemResource(User.class, id).toUri();
  }

  @PostConstruct
  private void init(){
    entityLinks.toString();
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks){
    User.entityLinks= entityLinks;
  }
}
