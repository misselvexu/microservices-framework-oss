package xyz.vopen.oss.gateway.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;
import xyz.vopen.oss.gateway.route.DefaultDynamicRouteService;

import java.util.concurrent.Executor;

/**
 * NacosDynamicRouteService
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-01-08.
 */
@Component
public class NacosDynamicRouteService {

  private static final Logger logger = LoggerFactory.getLogger(NacosDynamicRouteService.class);

  private final DefaultDynamicRouteService dynamicRouteService;

  private final NacosProperties nacosProperties;

  @Autowired
  public NacosDynamicRouteService(
      DefaultDynamicRouteService dynamicRouteService, NacosProperties nacosProperties) {
    this.dynamicRouteService = dynamicRouteService;
    this.nacosProperties = nacosProperties;
    dynamicRouteNacosListener(
        this.nacosProperties.getApplicationName(), NacosProperties.DEFAULT_GROUP);
  }

  /**
   * 监听Nacos Server下发的动态路由配置
   *
   * @param dataId data id
   * @param group group
   */
  public void dynamicRouteNacosListener(String dataId, String group) {
    try {
      ConfigService configService =
          NacosFactory.createConfigService(this.nacosProperties.getServerAddr());
      String content =
          configService.getConfig(dataId, group, this.nacosProperties.getRequestTimeout());
      logger.info("[OSS-NACOS] nacos gateway config content : {}", content);
      configService.addListener(
          dataId,
          group,
          new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
              logger.info(
                  "[OSS-NACOS-LISTENER] received changed config, {}-{} : {}",
                  dataId,
                  group,
                  configInfo);
              try {
                RouteDefinition definition = JSON.parseObject(configInfo, RouteDefinition.class);
                dynamicRouteService.update(definition);
              } catch (Exception e) {
                logger.error(
                    "[OSS-NACOS-LISTENER] invalid config content type, expect 'application/json'",
                    e);
              }
            }

            @Override
            public Executor getExecutor() {
              return null;
            }
          });
    } catch (NacosException e) {
      logger.warn("[OSS-NACOS] nacos listener execute failed", e);
    }
  }
}
