package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.Hunt;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HuntRepository extends JpaRepository<Hunt, UUID> {

  Iterable<Hunt> getAllByOrderByOrganizer();

  Iterable<Hunt> getAllByHuntNameContainsOrderByHuntName(String fragment);

  @Query(value = "SELECT * FROM sa.Hunt ORDER BY hunt_name", nativeQuery = true)
  Iterable<Hunt> getList();

  @Query(value = "SELECT * FROM sa.Hunt WHERE organizer id = :id ORDER BY hunt_name", nativeQuery = true)
  // Changed from getByOrganizer(long id) to this. --Trace
  Iterable<Hunt> getByOrganizer(UUID id);

  default Hunt findOrFail(UUID id) {
    return findById(id).get();
  }
}
