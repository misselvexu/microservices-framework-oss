package xyz.vopen.oss.services.auth.consumer.client;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import xyz.vopen.oss.services.auth.api.AuthApi;
import xyz.vopen.oss.services.auth.bean.Account;

/**
 * AuthServiceClient
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-11.
 */
@Component
public class AuthServiceClient {

  @Reference private AuthApi authApi;

  public Account info(Long passportUid) {
    return this.authApi.queryAccount(passportUid);
  }
}
