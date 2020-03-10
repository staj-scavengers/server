package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.HuntActivity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface  HuntActivityRepository extends JpaRepository<HuntActivity, UUID> {

  Iterable<HuntActivity> getAllByOrderByHunt();

  Iterable<HuntActivity> getAllByOrderByUser();

  Iterable<HuntActivity> getAllByOrderByStarted();

  Iterable<HuntActivity> getAllByOrderByCompleted();

  Iterable<HuntActivity> getAllByOrderByTotalTime();

  @Query(value = "SELECT * FROM HuntActivity WHERE hunt_id = :huntId", nativeQuery = true)
  Optional<HuntActivity> get(long huntId);

  default HuntActivity findOrFail(UUID id) {
    return findById(id).get();
  }
}
