package dev.staj.scavengr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 * The ScavengerApplication class provides a runnable method to initialize the server
 */
@EnableHypermediaSupport(type = HypermediaType.HAL)
@SpringBootApplication
public class ScavengerApplication {

  /**
   * The main method initializes the Scavenger server as a Spring application
   * @param args is not used by our implementation
   */
  public static void main(String[] args) {
    SpringApplication.run(ScavengerApplication.class, args);
  }


}
