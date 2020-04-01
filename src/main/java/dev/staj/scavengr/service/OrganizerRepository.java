package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.Organizer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Implements methods from {@link Organizer} entity.
 */
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {

  /**
   * @return all {@link Organizer}s.
   */
  Iterable<Organizer> getAll();

  /**
   * @param id is unique {@link Organizer}.
   * @return list of Organizers.
   */
    default Organizer findOrFail(UUID id){
      return findById(id).get();
    }


  }

