package io.github.stajscavengers.scavenger.service;

import io.github.stajscavengers.scavenger.model.entity.Organizer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {


    Iterable<Organizer> getAllByOrderById();

    @Query(value = "SELECT * FROM sa.Organizer WHERE organizer_id = :id",
        nativeQuery = true)
    // changed from get(long id); to this. --Trace
    Optional<Organizer> get(UUID id);

    default Organizer findOrFail(UUID id){
      return findById(id).get();
    }

  }

