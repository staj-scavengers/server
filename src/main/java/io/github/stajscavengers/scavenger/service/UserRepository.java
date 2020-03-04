package io.github.stajscavengers.scavenger.service;

import io.github.stajscavengers.scavenger.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

  Iterable<User> getAllByOrderByUserName();

  Iterable<User> getAllByUserNameContainsOrderByUserName(String fragment);

  @Query(value = "SELECT * FROM sa.User WHERE user_id = :id",
    nativeQuery = true)
  Optional<User> get(UUID id);

  default User findOrFail(UUID id){
    return findById(id).get();
  }

}
