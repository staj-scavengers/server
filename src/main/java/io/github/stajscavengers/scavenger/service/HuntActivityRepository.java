package io.github.stajscavengers.scavenger.service;

import io.github.stajscavengers.scavenger.model.entity.Hunt;
import io.github.stajscavengers.scavenger.model.entity.HuntActivity;
import io.github.stajscavengers.scavenger.model.entity.User;
import java.util.Date;
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

  default HuntActivity findOrFail(UUID id) {
    return findById(id).get();
  }
}
