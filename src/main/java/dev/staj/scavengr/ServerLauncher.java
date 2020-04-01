package dev.staj.scavengr;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServerLauncher {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ScavengerApplication.class)
        .profiles("server")
        .run(args);
  }
}
