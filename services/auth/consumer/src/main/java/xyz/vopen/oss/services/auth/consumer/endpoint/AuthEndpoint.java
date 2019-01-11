package xyz.vopen.oss.services.auth.consumer.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.vopen.oss.services.auth.bean.Account;
import xyz.vopen.oss.services.auth.consumer.client.AuthServiceClient;

/**
 * AuthEndpoint
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-11.
 */
@RestController
public class AuthEndpoint {

  private final AuthServiceClient authServiceClient;

  @Autowired
  public AuthEndpoint(AuthServiceClient authServiceClient) {
    this.authServiceClient = authServiceClient;
  }

  @GetMapping("/account/{passportUid}")
  ResponseEntity<Account> queryAccount(@PathVariable Long passportUid) {
    return ResponseEntity.ok(this.authServiceClient.info(passportUid));
  }
}
