package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

  Iterable<User> getAllByOrderByUserName();

  Iterable<User> getAllByUserNameContains(String fragment);

  @Query(value = "SELECT * FROM sa.User WHERE user_id = :id",
    nativeQuery = true)
  Optional<User> get(long id);

  default User findOrFail(UUID id){
    return findById(id).get();
  }

}