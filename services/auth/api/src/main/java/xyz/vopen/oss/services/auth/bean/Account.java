package xyz.vopen.oss.services.auth.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Account
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-11.
 */
@Getter
@Setter
public class Account implements Serializable {

  /** Passport Unique Id */
  private Long passportUid;

  /** Passport Account */
  private String passport;

  @Builder
  public Account(Long passportUid, String passport) {
    this.passportUid = passportUid;
    this.passport = passport;
  }
}
