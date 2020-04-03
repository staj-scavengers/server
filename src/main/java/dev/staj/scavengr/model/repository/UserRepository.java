package dev.staj.scavengr.model.repository;

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

  Optional<User> findFirstByOauthToken(String oauthToken);

  /**
   * @return ordered list of all {@link User} names in {@link User}.
   */
  Iterable<User> getAllByOrderByUserName();

  /**
   * @param fragment is a string used to search in url.
   * @return list of {@link User} names contained in {@link User} based on input of search.
   */
  Iterable<User> getAllByUserNameContains(String fragment);

  /**
   * @param id is unique {@link User}.
   * @return list of {@link User}s.
   */
  default User findOrFail(UUID id){
    return findById(id).get();
  }

}
