package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.HuntActivity;
import dev.staj.scavengr.model.entity.User;
import java.util.Date;
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
  Iterable<HuntActivity> getAllByHuntOrderByUser(Hunt hunt);

  /**
   *
   * @return list of all hunt activity ordered by started time/date.
   */
  Iterable<HuntActivity> getAllByOrderByStarted();

  /**
   *
   * @param user to return records for.
   * @return list of HuntActivity by {@link User}, ordered by {@link Hunt}.
   */
  Iterable<HuntActivity> getAllByUserOrderByHunt(User user);

  /**
   *
   * @param id unique hunt activity name.
   * @return list of hunt activity.
   */
  default HuntActivity findOrFail(UUID id) {
    return findById(id).get();
  }
}
