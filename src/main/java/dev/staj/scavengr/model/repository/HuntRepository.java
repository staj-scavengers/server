package dev.staj.scavengr.model.repository;

import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.Organizer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Implements methods from {@link Hunt} entity.
 */
public interface


HuntRepository extends JpaRepository<Hunt, UUID> {

  /**
   * @return all {@link Hunt}s ordered by {@link Organizer}.
   */
  Iterable<Hunt> getAllByOrderByOrganizer();

  /**
   *
   * @param fragment is a string used to search in url.
   * @return all {@link Hunt}s ordered by name.
   */
  Iterable<Hunt> getAllByHuntNameContainsAndIsOpenEqualsAndActiveEqualsOrderByHuntName(String fragment, Boolean isOpen, Boolean active);

  Iterable<Hunt> getAllByHuntNameContainsAndIsOpenEqualsOrderByHuntName(String fragment, Boolean isOpen);

  Iterable<Hunt> getAllByHuntNameContainsAndActiveEqualsOrderByHuntName(String fragment, Boolean active);

  Iterable<Hunt> getAllByHuntNameContainsOrderByHuntName(String fragment);

  /**
   * @param organizer unique {@link Organizer}.
   * @return all {@link Hunt}s by {@link Organizer}.
   */
  Iterable<Hunt> getAllByOrganizer(Organizer organizer);

  /**
   * @param id unique {@link Hunt} id.
   * @return list of {@link Hunt}s.
   */
  default Hunt findOrFail(UUID id) {
    return findById(id).get();
  }
}
