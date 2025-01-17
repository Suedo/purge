package example.demo.shared.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "app.tenants")
public class TenantProperties {
    private List<Tenant> tenants;

    @Data
    public static class Tenant {
        private String tenantId;
        private String tenantName;
        private String cron;
        private boolean purge;
    }
}
