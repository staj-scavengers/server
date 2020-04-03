package dev.staj.scavengr;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class PreloadLauncher {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ScavengerApplication.class)
        .profiles("preload")
        .un(args);
  }
}
