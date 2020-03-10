package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.HuntActivity;
import dev.staj.scavengr.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Implements methods from {@link HuntActivity} entity.
 */
public interface  HuntActivityRepository extends JpaRepository<HuntActivity, UUID> {

  /**
   *
   * @return all hunt activities ordered by {@link User}
   */
  Iterable<HuntActivity> getAllByOrderByHunt();

  /**
   *
   * @return list of all hunt activity ordered by {@link User}.
   */
  Iterable<HuntActivity> getAllByOrderByUser();

  /**
   *
   * @return list of all hunt activity ordered by started time/date.
   */
  Iterable<HuntActivity> getAllByOrderByStarted();

  /**
   *
   * @return list of all completed hunt activity.
   */
  Iterable<HuntActivity> getAllByOrderByCompleted();

  /**
   *
   * @return list of all hunt activity by total time to complete.
   */
  Iterable<HuntActivity> getAllByOrderByTotalTime();

  /**
   *
   * @param id unique hunt activity name.
   * @return list of hunt activity.
   */
  default HuntActivity findOrFail(UUID id) {
    return findById(id).get();
  }
}
