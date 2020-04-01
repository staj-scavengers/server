package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Implements methods from {@link Clue} entity.
 */
public interface ClueRepository extends JpaRepository<Clue, UUID> {

  /**
   * @return all {@link Clue} ordered by {@link Hunt}.
   */
  Iterable<Clue> getAllByOrderByHunt();

  /**
   *
   * @param hunt receives an existing {@link Hunt}.
   * @return all {@link Hunt}s ordered by id.
   */
  Iterable<Clue> getAllByHuntOrderByHuntOrder(Hunt hunt);

  /**
   * @param id unique {@link Clue} name.
   * @return list of clues by id.
   */
  default Clue findOrFail(UUID id) {
    return findById(id).get();
  }

}
