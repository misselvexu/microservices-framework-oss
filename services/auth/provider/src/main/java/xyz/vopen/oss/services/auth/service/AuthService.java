package xyz.vopen.oss.services.auth.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import xyz.vopen.oss.services.auth.api.AuthApi;
import xyz.vopen.oss.services.auth.bean.Account;
import xyz.vopen.oss.services.auth.exception.AuthException;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * AuthService
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-11.
 */
@Service
@Path("/auth")
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
  @GET
  @Path("/account/{passportUid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Account queryAccount(@PathParam("passportUid") Long passportUid) throws AuthException {
    return cache.get(passportUid);
  }
}
