package io.github.stajscavengers.scavenger.service;

import io.github.stajscavengers.scavenger.model.entity.Organizer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Implements methods from {@link Organizer} entity.
 */
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {

  /**
   * @return all organizers by their id.
   */
  Iterable<Organizer> getAllByOrderById();

  /**
   * @return a single orgainzer by id.
   */
    Optional<Organizer> get(UUID id);

  /**
   * @param id is unique organizer.
   * @return list of organizers.
   */
    default Organizer findOrFail(UUID id){
      return findById(id).get();
    }


  }

