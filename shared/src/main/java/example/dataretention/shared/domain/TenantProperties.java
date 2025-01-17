package example.dataretention.shared.domain;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Data
@Component
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

    @PostConstruct
    public void logProperties() {
        log.info("Loaded tenants: " + tenants);
    }
}
