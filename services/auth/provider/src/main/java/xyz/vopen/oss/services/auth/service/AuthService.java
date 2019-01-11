package xyz.vopen.oss.services.auth.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import xyz.vopen.oss.services.auth.api.AuthApi;
import xyz.vopen.oss.services.auth.bean.Account;
import xyz.vopen.oss.services.auth.exception.AuthException;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * AuthService
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-11.
 */
@Service
public class AuthService implements AuthApi {

  private Map<Long, Account> cache = Maps.newHashMap();

  @PostConstruct
  public void init() {
    cache.put(1L, Account.builder().passport("misselvexu").passportUid(1L).build());
  }

  /**
   * Query {@link Account} Detail
   *
   * @param passportUid passport unique id
   * @return a instance of {@link Account}
   * @throws AuthException auth exception throw
   */
  @Override
  public Account queryAccount(Long passportUid) throws AuthException {
    return cache.get(passportUid);
  }
}
