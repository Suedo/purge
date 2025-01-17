package example.dataretention.shared.config;

import example.dataretention.shared.domain.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ConfigurationProperties("app.tenants")
public record TenantProperties(List<Tenant> tenants) {
}
