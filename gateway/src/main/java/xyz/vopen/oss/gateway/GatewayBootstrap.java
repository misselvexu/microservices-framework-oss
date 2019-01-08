package xyz.vopen.oss.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import xyz.vopen.oss.gateway.nacos.NacosProperties;

/**
 * GatewayBootstrap
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-04.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(NacosProperties.class)
public class GatewayBootstrap {

  public static void main(String[] args) {
    SpringApplication.run(GatewayBootstrap.class, args);
  }
}
