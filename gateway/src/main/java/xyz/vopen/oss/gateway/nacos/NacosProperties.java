package xyz.vopen.oss.gateway.nacos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * NacosProperties
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-08.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "nacos")
public class NacosProperties {
  /** Nacos Server Address */
  private String serverAddr;

  private long requestTimeout = 3000;

  @Value("${spring.application.name}")
  private String applicationName;

  /** default group name */
  public static final String DEFAULT_GROUP = "DEFAULT_GROUP";
}
