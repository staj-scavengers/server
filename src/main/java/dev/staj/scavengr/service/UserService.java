package dev.staj.scavengr.service;

import dev.staj.scavengr.model.entity.User;
import dev.staj.scavengr.model.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  public synchronized User getOrCreate(String oauthToken, String displayName) {
    return userRepository.findFirstByOauthToken(oauthToken)
        .orElseGet(() -> {
          User user = new User();
          user.setOauthToken(oauthToken);
          user.setUserName(displayName);
          return userRepository.save(user);
        });
  }

  public Optional<User> get(UUID id) {
    return userRepository.findById(id);
  }
}
