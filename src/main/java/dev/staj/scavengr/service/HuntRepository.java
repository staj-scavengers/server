package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.Hunt;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Implements methods from {@link Hunt} entity.
 */
public interface HuntRepository extends JpaRepository<Hunt, UUID> {

  /**
   * @return all hunts ordered by organizer.
   */
  Iterable<Hunt> getAllByOrderByOrganizer();

  /**
   *
   * @param fragment is a string used to search in url.
   * @return all hunts ordered by name.
   */
  Iterable<Hunt> getAllByHuntNameContainsOrderByHuntName(String fragment);

  /**
   * @param id unique organizer.
   * @return all hunts by organizer.
   */
  Iterable<Hunt> getAllByOrganizer(UUID id);

  /**
   * @param id unique hunt id.
   * @return list of hunts.
   */
  default Hunt findOrFail(UUID id) {
    return findById(id).get();
  }
}
