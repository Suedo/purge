package example.dataretention.restapi.config;

import example.dataretention.restapi.config.properties.Deletion;
import example.dataretention.restapi.config.properties.Retry;
import example.dataretention.restapi.config.properties.Schedule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Data
@Service
@ConfigurationProperties("multi-tenant")
public class MultiTenantProperties {
    Schedule schedule;
    Retry retry;
    Deletion deletion;
}