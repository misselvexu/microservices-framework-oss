package xyz.vopen.oss.gateway.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * DefaultDynamicRouteService
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-04.
 */
@Component
public class DefaultDynamicRouteService implements ApplicationEventPublisherAware {

  private static final Logger logger = LoggerFactory.getLogger(DefaultDynamicRouteService.class);
  private final RouteDefinitionWriter routeDefinitionWriter;
  private ApplicationEventPublisher publisher;

  @Autowired
  public DefaultDynamicRouteService(RouteDefinitionWriter routeDefinitionWriter) {
    this.routeDefinitionWriter = routeDefinitionWriter;
  }

  /**
   * add new route definition
   *
   * @param definition a instance of {@link RouteDefinition}
   */
  public void add(RouteDefinition definition) {
    routeDefinitionWriter.save(Mono.just(definition)).subscribe();
    this.publisher.publishEvent(new RefreshRoutesEvent(this));
  }

  /**
   * Update RouteDefinition
   *
   * @param definition a instance of {@link RouteDefinition}
   */
  public void update(RouteDefinition definition) {
    try {
      this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
      logger.info("[OSS-GATEWAY] remove route:[{}] definition from writer.", definition.getId());
    } catch (Exception e) {
      logger.warn("[OSS-GATEWAY] upgrade gateway route failed ");
    }
    try {
      routeDefinitionWriter.save(Mono.just(definition)).subscribe();
      this.publisher.publishEvent(new RefreshRoutesEvent(this));
      logger.info("[OSS-GATEWAY] publish refresh routes event .");
    } catch (Exception e) {
      logger.warn("[OSS-GATEWAY] save gateway route definition failed ");
    }
  }

  /**
   * remove route from definition writer
   *
   * @param routerId router id
   * @return delete result
   */
  public Mono<ResponseEntity<Object>> delete(String routerId) {
    return this.routeDefinitionWriter
        .delete(Mono.just(routerId))
        .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
        .onErrorResume(
            throwable -> throwable instanceof NotFoundException,
            throwable -> Mono.just(ResponseEntity.notFound().build()));
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.publisher = applicationEventPublisher;
  }
}
