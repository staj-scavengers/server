package dev.staj.scavengr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.staj.scavengr.model.entity.Clue;
import dev.staj.scavengr.model.entity.Hunt;
import dev.staj.scavengr.model.entity.Organizer;
import dev.staj.scavengr.model.entity.User;
import dev.staj.scavengr.model.repository.ClueRepository;
import dev.staj.scavengr.model.repository.HuntRepository;
import dev.staj.scavengr.model.repository.OrganizerRepository;
import dev.staj.scavengr.model.repository.UserRepository;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Profile("preload")
public class Preloader implements CommandLineRunner {

  private static final String PRELOAD_DATA = "preload/preload.json";

  private final HuntRepository huntRepository;
  private final ClueRepository clueRepository;
  private final UserRepository userRepository;
  private final OrganizerRepository organizerRepository;
  private final String[] userNames = {"Samuel", "Trace", "Abby", "Jawid"};

  @Autowired
  public Preloader(HuntRepository huntRepository,
      ClueRepository clueRepository, UserRepository userRepository,
      OrganizerRepository organizerRepository) {
    this.huntRepository = huntRepository;
    this.clueRepository = clueRepository;
    this.userRepository = userRepository;
    this.organizerRepository = organizerRepository;
  }


  @Override
  public void run(String... args) throws Exception {
    ClassPathResource resource = new ClassPathResource(PRELOAD_DATA);
    try (InputStream input = resource.getInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
//      Map<String, UUID> clues = new HashMap<>();
      for (String userName : userNames) {
        User user = new User();
        user.setUserName(userName);
        userRepository.save(user);
        Organizer organizer = new Organizer();
        user.setOrganizer(organizer);
        organizerRepository.save(organizer);

        Clue[] clues = mapper.readValue(input, Clue[].class);
        Hunt hunt = new Hunt();
        hunt.setHuntName("Demo Hunt" + user.getUserName());
        hunt.setOrganizer(organizer);
        huntRepository.save(hunt);
        for (Clue clue : clues) {
//        clue.setHunt(hunt);
          hunt.addClue(clue);
          clueRepository.save(clue);
        }
      }
    }
  }
}


