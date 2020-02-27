package io.github.stajscavengers.scavenger.service;

import io.github.stajscavengers.scavenger.model.entity.Hunt;
import io.github.stajscavengers.scavenger.model.entity.Organizer;
import io.github.stajscavengers.scavenger.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {


    Iterable<Organizer> getAllByOrderBy();
    Iterable<Organizer> getAllByOrganizerNameContainsOrderByOrganizerName(String fragment);

    @Query(value = "SELECT * FROM sa.Organizer WHERE organizer_id = :id",
        nativeQuery = true)
    Optional<Organizer> get(long id);

    default Organizer findOrFail(UUID id){
      return findById(id).get();
    }
    

  }
