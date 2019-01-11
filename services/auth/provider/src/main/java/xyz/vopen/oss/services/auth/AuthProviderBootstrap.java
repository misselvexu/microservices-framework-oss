package xyz.vopen.oss.services.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * AuthProviderBootstrap
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-08.
 */
@EnableAutoConfiguration
public class AuthProviderBootstrap {

  public static void main(String[] args) {
    SpringApplication.run(AuthProviderBootstrap.class, args);
  }
}
