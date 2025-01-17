package example.dataretention.shared.config;

import example.dataretention.shared.domain.TableProperties;
import example.dataretention.shared.domain.TenantProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PurgeConfig {

    public final TenantProperties tenantProperties;
    public final TableProperties tableProperties;

    public PurgeConfig(TenantProperties tenantProperties, TableProperties tableProperties) {
        this.tenantProperties = tenantProperties;
        this.tableProperties = tableProperties;

        log.info("tenantProperties: {}", tenantProperties.getTenants().get(0));
        log.info("tableProperties: {}", tableProperties.getTables().get(0));
    }
}
