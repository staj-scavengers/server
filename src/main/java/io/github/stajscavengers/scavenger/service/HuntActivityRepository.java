package io.github.stajscavengers.scavenger.service;

import io.github.stajscavengers.scavenger.model.entity.Hunt;
import io.github.stajscavengers.scavenger.model.entity.HuntActivity;
import io.github.stajscavengers.scavenger.model.entity.User;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HuntActivityRepository extends JpaRepository<HuntActivity, UUID> {

  Iterable<HuntActivity> getAllByOrderByHunt(Hunt hunt);

  Iterable<HuntActivity> getAllByOrderByUser(User user);

  Iterable<HuntActivity> getAllByOrderByStarted(Date started);

  Iterable<HuntActivity> getAllByOrderByCompleted(Date completed);

  Iterable<HuntActivity> getAllByOrderByTotalTime(long totalTime);

  default HuntActivity findOrFail(UUID id) {
    return findById(id).get();
  }
}
