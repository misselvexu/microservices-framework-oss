package xyz.vopen.oss.services.auth.api;

import xyz.vopen.oss.services.auth.bean.Account;
import xyz.vopen.oss.services.auth.exception.AuthException;

/**
 * AuthApi
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-11.
 */
public interface AuthApi {

  /**
   * Query {@link Account} Detail
   *
   * @param passportUid passport unique id
   * @return a instance of {@link Account}
   * @throws AuthException auth exception throw
   */
  Account queryAccount(Long passportUid) throws AuthException;
}
