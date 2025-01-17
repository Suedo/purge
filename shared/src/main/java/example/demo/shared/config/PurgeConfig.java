package example.demo.shared.config;

import example.demo.shared.domain.TableProperties;
import example.demo.shared.domain.TenantProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties({TenantProperties.class, TableProperties.class})
public class PurgeConfig {
}
