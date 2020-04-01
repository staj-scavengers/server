package dev.staj.scavengr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@SpringBootApplication
@EnableWebSecurity
@EnableResourceServer
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class ScavengerApplication extends ResourceServerConfigurerAdapter{

  @Value("${oauth.clientId}")
  private String clientId;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(clientId);
  }

  public static void main(String[] args) {
    SpringApplication.run(ScavengerApplication.class, args);
  }

  // TODO enable after user roles are defined
/*  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
        .antMatchers(HttpMethod.POST, "/hunts").hasRole("ORGANIZER")
        .antMatchers(HttpMethod.PUT, "/hunts/**").hasRole("ORGANIZER")
        .antMatchers(HttpMethod.DELETE, "/hunts/**").hasRole("ORGANIZER")

        .antMatchers(HttpMethod.POST, "/clues").hasRole("ORGANIZER")
        .antMatchers(HttpMethod.PUT, "/clues/**").hasRole("ORGANIZER")
        .antMatchers(HttpMethod.DELETE, "/clues/**").hasRole("ORGANIZER")

        .antMatchers(HttpMethod.POST, "/hunt-activities").hasRole("ORGANIZER")
        .antMatchers(HttpMethod.PUT, "/hunt-activities/**").hasRole("ORGANIZER")
        .antMatchers(HttpMethod.DELETE, "/hunt-activities/**").hasRole("ORGANIZER")


        .antMatchers(HttpMethod.GET, "/hunts").hasRole("USER")
        .antMatchers(HttpMethod.GET, "/hunts/**").hasRole("USER")
        .antMatchers(HttpMethod.GET, "/clues/**").hasRole("USER")
        .antMatchers(HttpMethod.GET, "/hunt-activities").hasRole("USER");

  }*/

}
