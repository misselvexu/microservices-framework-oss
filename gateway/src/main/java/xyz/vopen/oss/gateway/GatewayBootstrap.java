package xyz.vopen.oss.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import xyz.vopen.oss.gateway.nacos.NacosProperties;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

  @SuppressWarnings("AlibabaThreadPoolCreation")
  @Configuration
  private static class DiscoveryTimer {
    @Autowired protected DiscoveryClient discoveryClient;

    private ScheduledExecutorService checker;

    public DiscoveryTimer() {
      checker = Executors.newSingleThreadScheduledExecutor();
      checker.scheduleWithFixedDelay(
          () -> {
            List<String> services = discoveryClient.getServices();
            if (services != null && !services.isEmpty()) {
              for (String service : services) {
                List<ServiceInstance> instances = discoveryClient.getInstances(service);
                if (instances != null && !instances.isEmpty()) {
                  System.out.println("Service:[" + service + "]");
                  for (ServiceInstance instance : instances) {
                    System.out.println("\t" + instance.getHost() + " -> " + instance.getPort());
                  }
                }
              }
            }
          },
          2,
          10,
          TimeUnit.SECONDS);
    }

    @PreDestroy
    void shutdown() {
      if (checker != null) {
        checker.shutdown();
      }
    }
  }
}
