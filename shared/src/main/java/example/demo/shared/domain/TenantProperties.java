package example.demo.shared.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app")
public record TenantProperties(
        List<Tenant> tenants,
        List<Table> tables
) {
    public record Tenant(
            String tenantId,
            String tenantName,
            String cron,
            boolean purge
    ) {
    }

    public record Table(
            String tableName,
            String retentionPeriod,
            String clause,
            int group,
            int subGroup
    ) {
    }
}
