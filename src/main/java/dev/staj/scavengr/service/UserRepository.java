package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Implements methods from {@link User} entity.
 *
 */
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * @return ordered list of all user names in {@link User}.
   */
  Iterable<User> getAllByOrderByUserName();

  /**
   * @param fragment is a string used to search in url.
   * @return list of user names contained in {@link User} based on input of search.
   */
  Iterable<User> getAllByUserNameContains(String fragment);

  // Trouble of thinking what else to add in Javadoc. -Abby

  /**
   * @param id is unique user.
   * @return list of users.
   */
  default User findOrFail(UUID id){
    return findById(id).get();
  }

}
