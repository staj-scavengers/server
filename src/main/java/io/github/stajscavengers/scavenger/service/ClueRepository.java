package io.github.stajscavengers.scavenger.service;

import io.github.stajscavengers.scavenger.model.entity.Clue;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClueRepository extends JpaRepository<Clue, UUID> {

  Iterable<Clue> findAllByOrderByHuntOrder();

  Iterable<Clue> getAllByhuntIdContainsOrderByHuntOrder(UUID id);

  default Clue findOrFail(UUID id) {
    return findById(id).get();
  }

}
