package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.Clue;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Implements methods from {@link Clue} entity.
 */
public interface ClueRepository extends JpaRepository<Clue, UUID> {

  /**
   * @return all clues ordered by hunt.
   */
  Iterable<Clue> getAllByOrderByHunt();

  /**
   * @param hunt_id receives an existing hunt.
   * @return all hunts ordered by id.
   */
  Iterable<Clue> getAllByHuntIdContainsOrderByHuntOrder(UUID hunt_id);

  /**
   * @param id unique clue name.
   * @return list of clues by id.
   */
  default Clue findOrFail(UUID id) {
    return findById(id).get();
  }

}
