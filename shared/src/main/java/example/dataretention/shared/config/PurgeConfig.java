package example.dataretention.shared.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@EnableConfigurationProperties({TenantProperties.class, TableProperties.class})
public class PurgeConfig {

    public final TenantProperties tenantProperties;
    public final TableProperties tableProperties;

    public PurgeConfig(TenantProperties tenantProperties, TableProperties tableProperties) {
        this.tenantProperties = tenantProperties;
        this.tableProperties = tableProperties;

        log.info("tenantProperties: {}", tenantProperties);
        log.info("tableProperties: {}", tableProperties);
    }
}
