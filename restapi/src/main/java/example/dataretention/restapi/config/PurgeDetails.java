package example.dataretention.restapi.config;


import example.dataretention.shared.domain.Table;
import example.dataretention.shared.domain.Tenant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@ConfigurationProperties("app")
public class PurgeDetails {
    private List<Tenant> tenants;
    private List<Table> tables;
}
